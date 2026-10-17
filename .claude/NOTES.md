# Teaching notes

- Learner is a **new joiner** to the project (role: developer). Prior experience with Spring/Vue not yet stated — ask in the next session and record a learning record.
- Learner asked for: documentation as `.md` files (→ `docs/`) and lessons as `.html` (→ `lessons/`), all inside `maku-boot-backend/.claude/`.
- Repo-specific facts verified in code on 2026-10-11 (see `docs/`). Re-verify when the code changes.
- Things worth flagging to the learner as "surprises" in this codebase:
  - Business errors and 401s come back as **HTTP 200** with `code != 0` in the JSON body (`Result`), not as HTTP status codes.
  - `access_token` is an opaque UUID, **not a JWT**; the user's permissions live in Redis under `sys:token:<uuid>`.
  - Passwords use **unsalted SM3** (`Sm3PasswordEncoder`); login passwords are **SM2-encrypted in the browser** (`src/utils/smCrypto.ts`).
  - Data scope on user list is commented out in `SysUserServiceImpl#getParams`.
  - `maku-module-iot` is built by the reactor but **commented out** of `maku-server/pom.xml`, so it is not in the running app.
