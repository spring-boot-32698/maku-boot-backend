# Mission: Deploy and operate maku on Kubernetes with Helm

## Why
I'm starting with Kubernetes and Helm because I need them at work (deploying apps like maku-boot to a cluster confidently) and for my career (interviews expect correct concepts and vocabulary). maku is my practice project: real Spring Boot + Vue + MySQL + Redis, not a toy.

## Success looks like
- I can explain, without notes, what each object in `deploy/helm/maku` does and why it's there (Deployment, StatefulSet, Service, Ingress, ConfigMap, Secret, PVC, probes, init container).
- I can bring maku up from zero on a local k3d cluster step by step (no script) and log in.
- I can ship a code change: new image tag → `helm upgrade` → verify → `helm rollback` if it breaks.
- When a pod is unhealthy (Pending, CrashLoopBackOff, ImagePullBackOff, OOMKilled, not Ready) I can find the cause with `kubectl get/describe/logs/events`.
- I can modify the chart (add a value, a template, a resource) and validate it with `helm lint` / `helm template`.
- I can answer common interview questions: Deployment vs StatefulSet, Service types, readiness vs liveness, ConfigMap vs Secret, what Helm adds over plain YAML, how a request reaches a pod.

## Constraints
- Brand new to Kubernetes; comfortable with Docker and Docker Compose (used them on this project).
- Practice cluster: local k3d cluster `maku` (`deploy/k3d/cluster.yaml`); machine has ~15 GB RAM.
- Short lessons (~10–15 min), hands-on with real commands against the real cluster.

## Out of scope (for now)
- Building clusters by hand (kubeadm, "the hard way"), cloud-provider specifics (EKS/GKE/AKS), service meshes, writing operators/CRDs.
- These can come later once the mission above is done.
