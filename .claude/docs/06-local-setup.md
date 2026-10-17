# 06 — Local setup

`docker-compose.yml` uses a **Compose profile** so one file covers both ways of working:

| You want to… | Command | Starts |
|---|---|---|
| Debug the backend in **IntelliJ** | `docker compose up -d` | `mysql` (3306) + `redis` (6379) |
| Run the **whole backend in Docker** | `docker compose --profile app up -d --build` | + `maku-boot` (8080, debug port 5005) |

Both modes share the same `mysql-data` volume, so data carries over when you switch. Don't run both at once (port 8080).

## Option A — backend in IntelliJ, dependencies in Docker (for debugging)

1. `cd maku-boot-backend && docker compose up -d`. Only MySQL and Redis start.
2. Open `maku-boot-backend` in IntelliJ as a Maven project (JDK 17+).
3. Pick the shared run configuration **ServerApplication (dev)** (from `.run/`, picked up automatically) and press **Debug**. It runs `net.maku.ServerApplication` with `SPRING_PROFILES_ACTIVE=dev`.
   - `application-dev.yml` points at `localhost:3306` / `localhost:6379`, so it uses the compose containers.
   - Uploaded files go to `~/maku/upload` (set in `application-dev.yml`; `application.yml`'s default `D://upload` only works on Windows).
   - Liquibase creates the schema on first start, the same as in Docker.
4. Stop the dependencies with `docker compose down`, or `docker compose down -v` to wipe the database.

## Option B — whole backend in Docker

```bash
cd maku-boot-backend
docker compose --profile app up -d --build   # mysql + redis + maku-boot on :8080
# tip: export COMPOSE_PROFILES=app  → then plain `docker compose up -d` includes the app too
```
- MySQL starts empty. When `maku-boot` starts, **Liquibase** creates the schema and seed data (baseline = `db/mysql-ver-english/*.sql`) and then applies any new changesets. See [05 — Database](05-database.md).
- Uploads go to the `upload-data` volume (`STORAGE_LOCAL_PATH=/app/upload`).
- **Debugging the container:** it listens for a debugger on `localhost:5005`. In IntelliJ: Run → Edit Configurations → + **Remote JVM Debug**, host `localhost`, port `5005`.
- Switch back to Option A: `docker compose --profile app stop maku-boot` (MySQL/Redis keep running), then start from IntelliJ.
- Stop everything: `docker compose --profile app down` (add `-v` to wipe data).

## Frontend

Either in Docker (nginx on :3000, API URL baked in at build time):
```bash
cd maku-admin-frontend && docker compose up -d --build
```
or with the Vite dev server:
```bash
cd maku-admin-frontend
npm install          # Node 22 LTS recommended
npm run dev          # http://localhost:3000  (VITE_API_URL=http://localhost:8080 in .env.development)
```
Both work against either backend option, since the API is on `localhost:8080` in both cases.

Log in with **admin / admin**, or one of the demo accounts (`manager`, `staff`, `auditor`, all with password `123456`). See [09 — Login accounts](09-accounts.md).

Build a jar: `mvn -DskipTests -pl maku-server -am package` → `maku-server/target/maku-server.jar`.

## Useful URLs (backend on :8080)

| URL | What |
|---|---|
| `http://localhost:8080/doc.html` | Knife4j API docs (groups: System, Quartz, Monitor, Member, Iot, Other) — note `knife4j.enable: false` in `application.yml`; if the page is blank use Swagger UI below |
| `http://localhost:8080/swagger-ui.html` | springdoc Swagger UI |
| `http://localhost:8080/v3/api-docs` | raw OpenAPI JSON |
| `http://localhost:8080/new/test` | sample endpoint in `maku-boot-new` (needs a token) |
| `ws://localhost:8080/ws?access_token=…` | WebSocket |

## Calling the API by hand

```bash
# 1) Log in — the password must be SM2-encrypted (see src/utils/smCrypto.ts), so it's easiest
#    to log in via the UI and copy the token from DevTools → Application → Local Storage → maToken.
TOKEN=...
# 2) Use it — raw token, no "Bearer"
curl -s -H "Authorization: $TOKEN" 'http://localhost:8080/sys/user/info' | jq
curl -s -H "Authorization: $TOKEN" 'http://localhost:8080/sys/post/page?page=1&limit=10' | jq
```

## Profiles & config

| File | Contents |
|---|---|
| `application.yml` | port 8080, active profile, multipart 1 GB, storage, XSS, token lifetimes, MyBatis-Plus |
| `application-dev.yml` | Redis (db 1), MySQL datasource (dynamic-datasource `master`), MQTT |
| `application-test.yml`, `application-prod.yml` | same shape for other environments |
| `auth.yml` (in several modules) | URLs that skip authentication |

Every property can be overridden by env var (Spring relaxed binding), e.g. `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_URL`, as `docker-compose.yml` does.
