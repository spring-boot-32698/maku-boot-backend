# 08 — Gotchas & known issues

Things that surprise new joiners, each verified in the code on 2026-10-11.

## Protocol surprises
1. **Errors are HTTP 200.** Unauthenticated, forbidden and business errors all return status 200 with `{"code": 401|403|500, "msg": …}`. Monitoring/alerting on HTTP status won't catch them. (`SecurityAuthenticationEntryPoint`, `ServerExceptionHandler`)
2. **Token header has no `Bearer ` prefix.** `Authorization: 3f2a…`. A `?access_token=` query param also works.
3. **Token JSON is snake_case** (`access_token`, `refresh_token`) while everything else is camelCase (`@JsonProperty` on `SysUserTokenVO`).
4. **Login password must be SM2-encrypted** with the public key hard-coded in `src/utils/smCrypto.ts`; the server decrypts with `Sm2Util`. You can't log in with plain `curl` without encrypting.

## Security notes (raise with the team before relying on them)
5. **Passwords are unsalted SM3** (`Sm3PasswordEncoder`). Identical passwords → identical hashes.
6. **SM2 key pair is hard-coded** in source on both sides; anyone with the repo can decrypt captured login payloads. Rotate per environment if this matters.
7. **Old access tokens survive re-login** until their 8 h TTL: `createToken` overwrites the DB row but doesn't delete the previous Redis key.
8. `DataScope.sqlFilter` is concatenated SQL. It's built only from numeric IDs today — keep it that way.
9. WebSocket allows any origin (`setAllowedOriginPatterns("*")`).

## Behaviour surprises
10. **`@Async` appears to have no effect**: no `@EnableAsync` was found in the codebase, so `updateCacheAuthByRoleId`, `OperateLogService.saveLog` etc. run synchronously on the request thread. Verify before relying on either behaviour.
11. **User list ignores data scope** — `params.put(Constant.DATA_SCOPE, …)` is commented out in `SysUserServiceImpl#getParams`. Role and org lists *do* apply it.
12. **Data scope with multiple roles = broadest wins** (`min(data_scope)`, `ALL = 0`).
13. **Permission changes reach live sessions on the backend immediately, but the frontend** keeps its menu/authority list until a full page reload.
14. **New dictionaries need a reload** in the browser (loaded once at boot) and a backend translation-cache refresh (`GET /sys/dict/type/refreshTransCache`) for `@Trans`.
15. **Operation logs lag ~10 s** and are lost if Redis is flushed (queued in Redis list `sys:log`).
16. **Quartz uses the RAM store** — two backend replicas will both fire every job.
17. **Custom XML queries must filter `deleted = 0`** themselves.
18. **`maku-module-iot` isn't running** — it's commented out in `maku-server/pom.xml` (and needs an MQTT broker; see `deploy/docker-compose-emqx.yml`).
19. **`storage.local.path` defaults to `D://upload`** — change it on Linux/macOS.
20. **`knife4j.enable: false`** — use `/swagger-ui.html` if `/doc.html` is blank.
21. **No tests run** — `skipTests=true` in the root `pom.xml`; the two tests in `maku-server/src/test` reference Windows paths.
22. **`tenant_id` columns exist but multi-tenancy isn't implemented** in this edition; don't assume isolation.
23. **Disabled users get a generic error.** Logging in as a disabled user returns `{"code":500,"msg":"Server error, please try again later"}`. `SysAuthServiceImpl.loginByAccount` only catches `BadCredentialsException`, so Spring's `DisabledException` falls through to the generic handler.
