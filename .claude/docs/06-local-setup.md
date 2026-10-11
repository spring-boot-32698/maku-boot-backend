# 06 — Local setup

## Option A — everything backend in Docker (fastest)

```bash
cd maku-boot-backend
docker compose up -d --build        # mysql:8.0 (3306) + redis:7 (6379) + maku-boot (8080)
```
- MySQL starts empty. When `maku-boot` starts, **Liquibase** creates the schema and seed data (baseline = `db/mysql-ver-english/*.sql`) and then applies any new changesets. See [05 — Database](05-database.md). To start from scratch: `docker compose down -v`.
- Uploads go to the `upload-data` volume (`STORAGE_LOCAL_PATH=/app/upload`).

Then the frontend, either in Docker (nginx on :3000, API URL baked in at build time):
```bash
cd maku-admin-frontend && docker compose up -d --build
```
or with the Vite dev server:
```bash
cd maku-admin-frontend
npm install          # Node 22 LTS recommended
npm run dev          # http://localhost:3000  (VITE_API_URL=http://localhost:8080 in .env.development)
```
Log in with **admin / admin**, or one of the demo accounts (`manager`, `staff`, `auditor`, all with password `123456`). See [09 — Login accounts](09-accounts.md).

## Option B — run the backend from your IDE

1. Start only the infrastructure: `docker compose up -d mysql redis`. Liquibase will create the schema when the app starts.
2. Open `maku-boot-backend` as a Maven project (JDK 17). The Aliyun mirror is configured in `pom.xml` / `maven-settings.xml`.
3. Run `net.maku.ServerApplication` (module `maku-server`), profile `dev` (default in `application.yml`).
4. **Change `storage.local.path`** — it defaults to `D://upload` (Windows). Set e.g. `STORAGE_LOCAL_PATH=/tmp/maku-upload` or edit `application.yml`.

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
