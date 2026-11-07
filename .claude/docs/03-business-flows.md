# 03 — Business flows

Every flow below is traced through real files. Backend paths are relative to `maku-boot-backend/`, frontend paths to `maku-admin-frontend/`.

---

## 1. Login (username + password)

```
Browser (views/login/account.vue)                    Backend
──────────────────────────────────                    ───────
GET /sys/auth/captcha/enabled ───────────────────────► SysParamsService LOGIN_CAPTCHA (seeded "false")
[if enabled] GET /sys/auth/captcha ──────────────────► SysCaptchaServiceImpl.generate()
                                                        key=UUID, 5-char image, Redis "sys:captcha:<key>" TTL 300s
password = sm2Encrypt(password)   (src/utils/smCrypto.ts, hard-coded SM2 public key)
POST /sys/auth/login {username,password,key,captcha} ► SysAuthController.login → SysAuthServiceImpl.loginByAccount
                                                        1. captcha validate (one-shot: key deleted on read)
                                                           fail → login log CAPTCHA_FAIL, "Wrong captcha"
                                                        2. authenticationManager.authenticate(
                                                              UsernamePasswordAuthenticationToken(username, Sm2Util.decrypt(pwd)))
                                                           → DaoAuthenticationProvider → UserDetailsServiceImpl
                                                             → load sys_user by username → SysUserDetailsServiceImpl.getUserDetails
                                                                (authorities + ROLE_<code> + dataScopeList)
                                                           → Sm3PasswordEncoder.matches (SM3 hex, unsalted)
                                                           AuthenticationEvents → sys_log_login (success / ACCOUNT_FAIL)
                                                        3. SysUserTokenService.createToken(userId)
                                                           new UUID access+refresh tokens, upsert sys_user_token (1 row per user)
                                                        4. TokenStoreCache.saveUser → Redis "sys:token:<access>" TTL 8h
◄─────────────── {access_token, refresh_token, ...expire} (snake_case via @JsonProperty)
userStore.setToken / setRefreshToken → localStorage (utils/cache)
router.beforeEach: no user.id yet →
  GET /sys/user/info, GET /sys/menu/authority, GET /sys/dict/type/all
  GET /sys/menu/nav → generateRoutes() → router.addRoute(...)
```

**Key consequences**
- One `sys_user_token` row per user: logging in again **replaces** the stored tokens in the DB, but the *old* Redis key is not deleted — it stays valid until its TTL expires.
- The user's permissions are **snapshotted into Redis at login**. Changing a role later is pushed into live sessions by `updateCacheAuthByRoleId/UserId` (see §4).

### Other login methods
All three go through the same `AuthenticationManager` (`maku-boot-system/.../security/config/SecurityConfig.java`) — a `ProviderManager` with:

| Provider | Token type | Endpoint | Verifies |
|---|---|---|---|
| `DaoAuthenticationProvider` | `UsernamePasswordAuthenticationToken` | `POST /sys/auth/login` | SM3 password |
| `MobileAuthenticationProvider` | `MobileAuthenticationToken` | `POST /sys/auth/send/code?mobile=` then `POST /sys/auth/mobile` | 6-digit SMS code from Redis (`SmsSendCache`, TTL 10 min, one-shot) |
| `ThirdAuthenticationProvider` | `ThirdAuthenticationToken` | `GET /sys/third/render/{source}` → provider → `/sys/third/callback/{source}` (renders `third_login` template) → `POST /sys/auth/third {openType, code, state}` | JustAuth exchanges code → openId → `sys_third_login` lookup |

The SMS-code endpoint refuses unknown mobiles ("Mobile number is not registered"). A logged-in user links a third-party account via `POST /sys/third/bind` and removes it via `PUT /sys/third/unbind/{openType}`.

---

## 2. Tokens: access, refresh, logout

| Token | Format | Lifetime (config `maku.security.*`) | Stored in |
|---|---|---|---|
| access token | UUID (no dashes) | 28800 s = **8 h** | Redis `sys:token:<token>` → `UserDetail` *and* `sys_user_token.access_token` |
| refresh token | UUID | 1209600 s = **14 days** | `sys_user_token.refresh_token` only |

**Sending it:** header `Authorization: <token>` (no `Bearer ` prefix) **or** query param `access_token=<token>` (used by WebSocket and file downloads) — `TokenUtils.getAccessToken`.

**Refresh** (`src/utils/request.ts`):
1. Any response with `code === 401` → if not already refreshing, call `POST /sys/auth/token?refreshToken=…`.
2. Backend `SysUserTokenServiceImpl.refreshToken`: find row by refresh token where `refresh_token_expire >= now` (else `code 400`) → delete old Redis key → new access token → **reload permissions from DB** → save to Redis.
3. Frontend stores the new token, replays the failed request and any requests that queued up meanwhile.
4. `code === 400` → "Login timed out" dialog → clear tokens → reload → `/login`.

**Logout** `POST /sys/auth/logout`: delete Redis key, set both expiries in `sys_user_token` to now, write login log `LOGOUT_SUCCESS`.

**Kick a user** (monitor module): `DELETE /monitor/user/{accessToken}` removes their Redis entry; online users are listed by scanning `sys:token:*`.

---

## 3. Authorization: menus, buttons, permission codes (RBAC)

### Data model
```
sys_user ──< sys_user_role >── sys_role ──< sys_role_menu >── sys_menu
                                   │                            type 0 = menu (has url → Vue page)
                                   │                            type 1 = button (has authority codes)
                                   │                            type 2 = API
                                   └──< sys_role_data_scope >── sys_org   (only for CUSTOM data scope)
```
`sys_menu.authority` holds **comma-separated permission codes**, e.g. `sys:menu:update,sys:menu:info`.

### Backend enforcement
- Controllers: `@PreAuthorize("hasAuthority('sys:user:save')")` (enabled by `@EnableMethodSecurity`).
- A user's authority set = all codes from their roles' menus (`SysMenuServiceImpl.getUserAuthority`) **+** `ROLE_<roleCode>` for each role.
- **Super admin** (`sys_user.super_admin = 1`): gets every code found in any `@PreAuthorize` in the app (`PreAuthorizeUtil` scans controllers by regex) and sees all menus.
- Endpoints **without** `@PreAuthorize` only require being logged in (e.g. `GET /sys/post/page`, `/sys/user/page`, `/sys/org/list`).

### Frontend enforcement (cosmetic — the backend is the real gate)
- `GET /sys/menu/nav` → menu tree (type 0) → **routes** (`src/router/index.ts#generateRoutes`). A menu's `url` like `sys/user/index` maps to `src/views/sys/user/index.vue`. External URLs open in an iframe (`openStyle 0`) or new tab (`openStyle 1`).
- `GET /sys/menu/authority` → `userStore.authorityList` → `v-auth="'sys:user:save'"` **removes the element from the DOM** if the code is missing (`src/utils/directive/auth.ts`).

### Admin flow: grant a new screen to a role
1. Menu Management: add menu (type 0, url = `module/feature/index`) and its buttons (type 1, authority codes matching the controller's `@PreAuthorize`).
2. Role Management: tick the menus/buttons for a role (`PUT /sys/role` with `menuIdList`).
3. Backend saves `sys_role_menu` and calls `updateCacheAuthByRoleId(roleId)` → every online user with that role gets their Redis `UserDetail` rebuilt (TTL preserved). They see new buttons after a page reload (the frontend caches menus/authority until reload).

---

## 4. Data scope (row-level security by organisation)

Set per role (`PUT /sys/role/data-scope`). New roles default to `SELF` (`SysRoleServiceImpl.save`).

| Value | `DataScopeEnum` | Rows visible |
|---|---|---|
| 0 | `ALL` | everything (no filter) |
| 1 | `ORG_AND_CHILD` | user's org + all descendant orgs (+ custom orgs) + own rows |
| 2 | `ORG_ONLY` | user's org (+ custom orgs) + own rows |
| 3 | `SELF` | only rows where `creator = me` |
| 4 | `CUSTOM` | orgs picked in `sys_role_data_scope` + own rows |

**Multiple roles:** `SysRoleDao.getDataScopeByUserId` takes `min(data_scope)` → the **broadest** scope wins (0 is broadest).

**How it is applied** (`maku-framework/.../mybatis`):
1. At login/refresh, `SysUserDetailsServiceImpl.getDataScope` computes `dataScopeList` (`null` = all; list of org IDs otherwise) and stores it in the cached `UserDetail`.
2. A service opts in, either:
   - Lambda wrapper: `dataScopeWrapper(wrapper)` → appends `(org_id in(1,2,3) or creator=42)`; or
   - XML query: `params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"))` → `DataScopeInnerInterceptor` finds the `DataScope` object among the mapper params and **rewrites the SQL's WHERE clause** with JSqlParser.
3. Super admins and `ALL` return `null` → no filter.

Currently opted-in: role list (`SysRoleServiceImpl`) and org list (`SysOrgServiceImpl`, filtering on `t1.id`). The user list has it **commented out** (`SysUserServiceImpl#getParams`). Your new tables need an `org_id` (auto-filled on insert from the current user) and `creator` column to support data scope.

---

## 5. Organisation, users, posts

- **Org (`sys_org`)**: tree via `pid`. `GET /sys/org/list` returns a tree (data-scoped). `getSubOrgIdList` powers `ORG_AND_CHILD`.
- **Post (`sys_post`)**: job titles; many-to-many with users via `sys_user_post`.
- **User (`sys_user`)**:
  - Create: username and mobile must be unique; `super_admin` forced to 0; saves `sys_user_role` + `sys_user_post`.
  - Update: same checks, then `updateCacheAuthByUserId` refreshes live sessions.
  - Self-service: `GET/PUT /sys/user/info`, `PUT /sys/user/avatar|signature|password`.
  - Import/export Excel: `POST /sys/user/import`, `GET /sys/user/export` (`SysUserExcelVO` uses `@Trans` to turn `gender/status` codes into labels).
  - Status: `UserStatusEnum` — disabled users get `enabled=false` → Spring Security rejects login.

---

## 6. Dictionaries and translation

- `sys_dict_type` (e.g. `user_gender`, `post_status`, `success_fail`) + `sys_dict_data` (label/value/labelClass/sort).
- A type's source is either `DICT` (rows in `sys_dict_data`) or `SQL` (`dict_sql` executed at load time to produce label/value pairs).
- Frontend loads **all** dictionaries once after login (`GET /sys/dict/type/all` → `appStore.dictList`) and renders them with `ma-dict-select`, `ma-dict-column`, `ma-dict-label`, `ma-dict-radio`, `ma-dict-checkbox`.
- Backend translation: annotate VO fields with `@Trans`; `TransResponseBodyAdvice` fills the `ref` field on JSON responses that contain a `TransPojo`. For non-JSON output (Excel export) call `transService.transBatch(list)` yourself, as `SysUserServiceImpl#export` does.
  - `TransType.DICTIONARY` — dict value → label from the in-memory `DictionaryTransService` map (refresh with `GET /sys/dict/type/refreshTransCache`).
  - `TransType.SIMPLE` — foreign key → field of another entity (e.g. `orgId` → `SysOrgEntity.name` into `orgName`).

## 7. System parameters

`sys_params` (key/value, e.g. `LOGIN_CAPTCHA`). Read via `SysParamsService.getString/getBoolean/getInt/getJSONObject`; values are cached in Redis by `SysParamsCache` and the cache is updated on save/delete. Use this instead of adding feature flags to YAML when admins must change them at runtime.

## 8. Files and attachments

- `POST /sys/file/upload` (multipart `file`) → `StorageService.getPath(name)` = `[prefix/]yyyyMMdd/<name>_<secondsOfDay>.<ext>` → `upload()` → returns `{url, size, name, platform}`.
- Backend chosen by `storage.config.type` (`local | aliyun | tencent | qiniu | huawei | minio`). Local files are written under `storage.local.path` and served by `LocalResourceConfiguration` at `/<storage.local.url>/**` (`/upload/**` by default, public — listed in `auth.yml`).
- `sys_attachment` is a separate catalogue (`/sys/attachment`) for keeping uploaded file metadata.
- Frontend components: `ma-upload-image`, `ma-upload-images`, `ma-upload-file`, `ma-upload-excel`.

## 9. SMS and email

- Platform configs are DB rows (`sys_sms_config`, `sys_mail_config`), cached in Redis. Multiple enabled platforms are used **round-robin** (Redis `INCR` counter); configs can be grouped (`groupName`).
- SMS vendor chosen per config via the Strategy pattern (`SmsStrategy` + `SmsContext`). Every send writes `sys_sms_log` / `sys_mail_log`.
- Other modules send SMS through `SmsApi` (in `maku-boot-api`) so they don't depend on `maku-boot-system` internals.

## 10. Audit: login log & operation log

- **Login log** (`sys_log_login`): written by `AuthenticationEvents` (success/failure events from the `ProviderManager`) and by `SysAuthServiceImpl` (captcha fail, logout). Includes IP + region (ip2region) and user agent.
- **Operation log** (`sys_log_operate`): put `@OperateLog(type = …)` on a controller method → `OperateLogAspect` records user, module (from `@Tag`), name (from `@Operation`), URI, params (files/requests ignored), duration, status → **pushes to Redis list `sys:log`** → `SysLogOperateServiceImpl` drains up to 10 entries every 10 s into MySQL. Logs written while Redis is down are lost; logs can lag by ~10 s.

## 11. Scheduled jobs (Quartz)

- `schedule_job` row = `{jobName, jobGroup, beanName, method, params, cronExpression, concurrent, status}`.
- On run, `AbstractScheduleJob` does `SpringUtil.getBean(beanName).getClass().getDeclaredMethod(method, String.class).invoke(bean, params)` → so a job is **any Spring bean with a `public void x(String params)` method** (see `quartz/task/TestTask.java`).
- `concurrent` picks `ScheduleConcurrentExecution` vs `ScheduleDisallowConcurrentExecution` (`@DisallowConcurrentExecution`).
- Quartz runs with the in-memory (RAM) job store — the JDBC/cluster settings in `quartz/config/ScheduleConfig.java` are commented out. `ScheduleJobServiceImpl#init` (`@PostConstruct`) re-registers every `schedule_job` row at startup, so the table is the source of truth. Running two backend instances will run each job twice.
- Each run writes `schedule_job_log` (duration, status, error truncated to 2000 chars).
- API: `/schedule` CRUD, `PUT /schedule/run` (run now), `PUT /schedule/change-status`; `/schedule/log/page`.

## 12. WebSocket messaging

- Endpoint `ws://host:8080/ws?access_token=<token>` (`WebSocketConfig`, allowed origins `*`). `/ws` is not in `auth.yml`, so the token filter must authenticate the handshake; `UserHandshakeInterceptor` stores the current user in the session attributes.
- `WebSocketSessionManager` tracks sessions per user; `POST /ws/message/send` pushes a message to users. Demo page: `src/views/demo/websocket/index.vue`.

## 13. Monitoring

`/monitor/server/*` (CPU, memory, JVM, disk via OSHI), `/monitor/cache/*` (Redis info, keys, values, delete), `/monitor/user/*` (online users from `sys:token:*`, force logout).

## 14. Members (end-users)

`maku-module-member` → `member_user` table + `/member/user` CRUD. Members are **not** `sys_user`s: they are a separate account table for app/mobile end-users managed from the admin UI.
