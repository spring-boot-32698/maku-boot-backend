# Deploying maku to Kubernetes

Helm chart: `deploy/helm/maku`. It deploys the **maku-boot** API and the **maku-admin** UI, plus (optionally) MySQL and Redis inside the cluster.

```
                        ┌──────────────── namespace: maku ────────────────────────────────┐
 browser                │                                                                  │
 maku.localhost:8088 ──►│ Ingress "maku" ──► Service maku-frontend ──► Deployment (nginx)  │
 api.maku.localhost ───►│   (Traefik)    ──► Service maku-backend  ──► Deployment (Spring) │
                        │                                    │  ▲ ConfigMap maku-backend   │
                        │                                    │  ▲ Secret    maku-db        │
                        │                                    │  └ PVC maku-uploads (files) │
                        │              Service maku-mysql ◄───┤                             │
                        │                └► StatefulSet maku-mysql ── PVC data-maku-mysql-0 │
                        │              Service maku-redis ◄───┘                             │
                        │                └► Deployment maku-redis (no persistence)          │
                        └──────────────────────────────────────────────────────────────────┘
```

| File | What |
|---|---|
| `Chart.yaml` | Chart name/version (`version` = chart, `appVersion` = default image tag) |
| `values.yaml` | All settings with safe-ish defaults (ingress off, dev passwords) |
| `values-k3d.yaml` | Local k3d cluster: Traefik ingress on `*.localhost:8088` |
| `values-production.example.yaml` | Example: registry images, managed DB/Redis, TLS, `existingSecret` |
| `templates/` | `backend-*.yaml`, `uploads-pvc.yaml`, `frontend.yaml`, `mysql.yaml`, `redis.yaml`, `secret.yaml`, `ingress.yaml`, `tests/`, `NOTES.txt`, `_helpers.tpl` |
| `../k3d/cluster.yaml` | k3d cluster definition (1 server + 1 agent, port 8088 → ingress) |
| `../k3d/up.sh` / `down.sh` | Build → import → `helm upgrade --install`; tear down |

## Quick start (local k3d)

Prereqs: Docker, `k3d` ≥ 5.6, `kubectl`, `helm` (v3 or v4). ~4 GB free RAM.

```bash
cd maku-boot-backend
deploy/k3d/up.sh
```

Then open **http://maku.localhost:8088** and log in as `admin` / `admin` (or `manager` / `123456`; see `.claude/docs/09-accounts.md`). The API is at http://api.maku.localhost:8088 (health: `/actuator/health`).

## The same thing, step by step (what `up.sh` does)

```bash
# 1. Cluster
k3d cluster create --config deploy/k3d/cluster.yaml
kubectl config use-context k3d-maku
kubectl get nodes                                    # 2 nodes Ready

# 2. Images (the UI's API URL is compiled in, so build it for this cluster's URL)
docker build -t maku-boot:5.0.0 .
docker build -t maku-admin:5.0.0 --build-arg VITE_API_URL=http://api.maku.localhost:8088 ../maku-admin-frontend

# 3. Load images into the cluster nodes (no registry needed with k3d)
k3d image import maku-boot:5.0.0 maku-admin:5.0.0 -c maku

# 4. Preview what Helm will create, then install
helm template maku deploy/helm/maku -f deploy/helm/maku/values-k3d.yaml | less
helm upgrade --install maku deploy/helm/maku -n maku --create-namespace \
     -f deploy/helm/maku/values-k3d.yaml --wait --timeout 10m

# 5. Verify
kubectl -n maku get pods                             # 4 pods Running, READY 1/1
helm test maku -n maku                               # Phase: Succeeded
curl http://api.maku.localhost:8088/actuator/health  # {"status":"UP",...}
```

First install takes ~1–2 min: MySQL initialises, the backend's init container waits for it, then Liquibase creates the schema.

## Day-2 operations

| Task | Command |
|---|---|
| Watch pods | `kubectl -n maku get pods -w` |
| Backend logs | `kubectl -n maku logs deploy/maku-backend -f` |
| Why is a pod stuck? | `kubectl -n maku describe pod <pod>` (read **Events** at the bottom) |
| Shell into MySQL | `kubectl -n maku exec -it maku-mysql-0 -- sh -c 'mysql -umaku -p"$MYSQL_PASSWORD" maku_boot'` |
| Change a setting | edit values → `helm upgrade maku deploy/helm/maku -n maku -f deploy/helm/maku/values-k3d.yaml` |
| One-off override | `... --set backend.extraEnv[0].name=LOGGING_LEVEL_NET_MAKU --set backend.extraEnv[0].value=debug` |
| Deploy new code | rebuild image with a **new tag** → `k3d image import` → `helm upgrade ... --set backend.image.tag=5.0.1` |
| History / rollback | `helm history maku -n maku` · `helm rollback maku <revision> -n maku` |
| Rendered manifests of the live release | `helm get manifest maku -n maku` |
| Values of the live release | `helm get values maku -n maku` |
| Access without ingress | `kubectl -n maku port-forward svc/maku-backend 8080:8080` |
| Remove release (keeps PVCs) | `deploy/k3d/down.sh` · data too: `kubectl -n maku delete pvc --all` |
| Remove cluster | `deploy/k3d/down.sh --cluster` |

> Re-using the same image tag after a rebuild? Kubernetes won't notice. Use a new tag, or `kubectl -n maku rollout restart deploy/maku-backend` after re-importing.

## Design decisions (and why)

| Decision | Why |
|---|---|
| **Two ingress hosts** (UI + API) | The API serves from `/` with no `/api` prefix, so path-based routing would collide with UI routes. |
| **`replicaCount: 1`** for the backend | Quartz uses an in-memory job store (jobs would run once per replica), and uploads live on a ReadWriteOnce volume. Scaling out needs a Quartz JDBC store and shared storage (RWX or object storage via `storage.config.type`). |
| **`strategy: Recreate`** for the backend | The RWO uploads volume can't be attached to the old and new pod at once if they land on different nodes. |
| **MySQL as a StatefulSet** with `volumeClaimTemplates` | Stable pod name (`maku-mysql-0`) and a PVC that survives pod deletion. |
| **Redis as a Deployment** without a volume | It only holds sessions, caches and a log queue. A restart logs users out and nothing else is lost. |
| **Init container `wait-for-mysql`** | Avoids a burst of CrashLoopBackOff restarts while MySQL initialises on first install. |
| **Startup probe up to 5 min**, then liveness/readiness on `/actuator/health/{liveness,readiness}` | Liquibase plus JVM start is slow the first time. Readiness also checks DB and Redis (`management.endpoint.health.group.readiness`). |
| **`checksum/config` annotation** | Changing the ConfigMap rolls the backend pods; env vars are only read at start. |
| **Secret with `existingSecret` escape hatch** | Dev defaults just work; production should supply its own Secret (Sealed Secrets, External Secrets, Vault…). |
| **`helm.sh/resource-policy: keep` on uploads PVC** | `helm uninstall` shouldn't delete user files. |
| **Liquibase context = Spring profile** | `dev` installs the demo accounts; `prod` doesn't. |

## Production checklist (not done for you)

- Push images to a registry and set `image.repository` / `tag`; add `imagePullSecrets` if private.
- Build the UI with the real API URL (`VITE_API_URL`).
- Use a managed MySQL/Redis (`mysql.enabled=false`, `externalDatabase.host`, …) or at least back up the PVC.
- Provide `database.existingSecret`; never ship `values.yaml` passwords.
- TLS via cert-manager (`ingress.tls`, annotations).
- Set `springProfile: prod` and review `application-prod.yml`.
- Add `securityContext` (non-root), `PodDisruptionBudget`, `NetworkPolicy` as your platform requires.
