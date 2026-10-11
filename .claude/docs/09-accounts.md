# 09 — Login accounts (local / dev)

> **Dev only.** The demo accounts are created by the Liquibase changeset
> `maku-server/src/main/resources/db/changelog/changes/0006-dev-demo-accounts.sql`, tagged `context:dev`.
> `spring.liquibase.contexts` = the active profile, so they exist **only when running with the `dev` profile**
> (the default, and what `docker-compose.yml` uses). Never reuse these passwords anywhere real.

Admin UI: http://localhost:3000 · API: http://localhost:8080

## Accounts

| Username | Password | Name | Org | Role (data scope) | What you can do |
|---|---|---|---|---|---|
| `admin` | `admin` | admin | — | **Super admin** (sees everything) | Every menu and button; all rows |
| `manager` | `123456` | Mia Manager | Sales | HR Manager (`ORG_AND_CHILD`) | Full **Access Management**: users, orgs, posts, roles (add/edit/delete/import/export) |
| `staff` | `123456` | Sam Staff | Sales North | Staff (`SELF`) | **Read-only** Users, Orgs and Posts (Query buttons only) |
| `auditor` | `123456` | Ada Auditor | HQ | Auditor (`ALL`) | **Log Management** (login and operation logs) + **System monitoring** (server, cache, online users) |
| `disabled` | `123456` | Dan Disabled | Operations | Staff (`SELF`) | Nothing: the account has status *disabled*, so login is refused |

`admin` comes from the baseline seed (`db/mysql-ver-english/maku.sql`). The other four come from changeset `0006`.

### Organisation tree

```
HQ (101)
├── Sales (102)              ← manager
│   └── Sales North (103)    ← staff
└── Operations (104)         ← disabled
                             auditor sits in HQ
```

## Verified behaviour (2026-10-11, Docker stack)

| Check | admin | manager | staff | auditor | disabled |
|---|---|---|---|---|---|
| Login | ✅ | ✅ | ✅ | ✅ | ❌ `{"code":500,"msg":"Server error…"}` |
| Top-level menus | all 6 | Access Management | Access Management (no Role Mgmt) | Application Mgmt › System monitoring, Log Management | — |
| Orgs visible in *Organization Management* (data scope) | all 4 | Sales, Sales North | none¹ | all 4 | — |
| `POST /sys/post` (needs `sys:post:save`) | ✅ | ✅ | ❌ 403 | ❌ 403 | — |

¹ `SELF` scope filters the org tree to rows the user created, and `staff` created none.

## Use them to learn

- **Permissions** ([lesson 03](../lessons/0003-permissions.html)): log in as `staff` and the Add/Edit/Delete buttons are gone. Calling the API directly with staff's token returns `{"code":403}`.
- **Data scope** ([lesson 04](../lessons/0004-data-scope.html)): compare *Organization Management* as `manager` (sub-tree only), `staff` (empty) and `auditor` (everything). Change a role's data scope as `admin` (*Role Management → Data permissions*) and watch the result change for a logged-in user.
- **Live permission refresh** ([lesson 02](../lessons/0002-login-and-tokens.html)): while `manager` is logged in, remove a button from *HR Manager* as `admin`. The API rejects that action immediately; the button disappears after `manager` reloads the page.
- **Note:** the user list (`/sys/user/page`) does **not** apply data scope (see [08 Gotchas](08-gotchas.md) #11), so `manager` sees all users there.

## Login via API (scripts / curl)

The password must be SM2-encrypted with the public key from `maku-admin-frontend/src/utils/smCrypto.ts`:

```bash
npm i sm-crypto@0.3.13   # in a scratch folder
P=$(node -e "const {sm2}=require('sm-crypto');console.log('04'+sm2.doEncrypt('123456','040a302b5e4b961afb3908a4ae191266ac5866be100fc52e3b8dba9707c8620e64ae790ceffc3bfbf262dc098d293dd3e303356cb91b54861c767997799d2f0060',1))")
curl -s -H 'Content-Type: application/json' \
     -d "{\"username\":\"manager\",\"password\":\"$P\"}" localhost:8080/sys/auth/login
# → {"code":0,"data":{"access_token":"…","refresh_token":"…"}}
curl -s -H "Authorization: <access_token>" localhost:8080/sys/user/info
```

Or log in through the UI and copy `maToken` from DevTools → Application → Local Storage.

## Resetting

- Reset everything: `docker compose down -v && docker compose up -d --build` (Liquibase recreates the baseline and the demo accounts).
- Forgot a changed password: as `admin`, *User Management → Edit → password*. Or set `sys_user.password` to `207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb` (= SM3 of `123456`).
- Adding more demo users: create a **new** changeset file in `db/changelog/changes/` with `context:dev`. Don't edit `0006`; Liquibase checksums would fail.
