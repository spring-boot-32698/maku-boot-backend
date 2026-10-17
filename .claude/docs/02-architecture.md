# 02 — Architecture

## 1. Repositories

```
maku/
├── maku-boot-backend/     ← Spring Boot API (this repo; docs live in .claude/)
└── maku-admin-frontend/   ← Vue 3 admin SPA, talks to the API over HTTP + WebSocket
```

## 2. Backend Maven modules

```
maku-boot (root pom, packaging=pom, parent=spring-boot-starter-parent 4.0.6)
│
├── maku-framework        Shared infrastructure, no business logic.
│                         security (token filter, providers, SM2/SM3), mybatis (BaseEntity, BaseServiceImpl,
│                         data-scope interceptor, auto-fill), common (Result, ServerException, Redis cache,
│                         XSS filter, CORS, Jackson, Excel, @Trans translation), operatelog (@OperateLog aspect)
│
├── maku-boot-api         Thin interfaces other modules can depend on without depending on system:
│                         net.maku.api.module.system.SmsApi, StorageApi
│
├── maku-boot-system      The core business module ("system management"):
│   ├── system/           users, orgs, posts, roles, menus, dictionaries, params, logs, attachments,
│   │                     SMS/mail config+logs, third-party login (controller/service/dao/entity/vo/query/enums)
│   ├── security/         SecurityConfig (AuthenticationManager w/ 3 providers), UserDetailsService impls,
│   │                     AuthenticationEvents (writes login log)
│   ├── storage/          StorageService + Local/Aliyun/Tencent/Qiniu/Huawei/Minio impls
│   ├── sms/              SmsStrategy + Aliyun/Tencent/Qiniu/Huawei, SmsContext
│   └── email/            EmailService (Aliyun / Tencent / SMTP)
│
├── maku-boot-module      Optional feature modules ("include it if you need it"):
│   ├── maku-module-quartz      schedule_job / schedule_job_log, cron jobs that call Spring bean methods
│   ├── maku-module-websocket   /ws endpoint, session manager, POST /ws/message/send
│   ├── maku-module-monitor     /monitor/server (OSHI), /monitor/cache (Redis), /monitor/user (online users)
│   ├── maku-module-member      member_user CRUD (app/end-user accounts, separate from sys_user)
│   ├── maku-module-generator   wraps the closed-source maku-generator-pro starter (/maku-generator/**)
│   └── maku-module-iot         MQTT/TCP device management — NOT included in maku-server
│
├── maku-boot-new         Empty skeleton for YOUR new business code (package net.maku.module,
│                         sample TestController at /new/test). Depends only on maku-framework.
│
└── maku-server           The runnable app: ServerApplication (@SpringBootApplication in package net.maku),
                          SwaggerConfig, application*.yml. Depends on system, new, and the modules above.
```

Dependency direction (arrows = "depends on"):

```
maku-server ──► maku-boot-system ──► maku-framework
     │                  └──────────► maku-boot-api
     ├────────► maku-boot-new ─────► maku-framework
     └────────► maku-module-* ─────► maku-framework
```

Because `ServerApplication` lives in package `net.maku`, component scanning picks up every `net.maku.*` bean from every module on the classpath. **Adding a module = adding a dependency to `maku-server/pom.xml`.**

## 3. Package layout inside a business module

Every feature follows the same shape (example: posts in `maku-boot-system/src/main/java/net/maku/system/`):

| Layer | Package | Example | Notes |
|---|---|---|---|
| Controller | `controller` | `SysPostController` | `@RestController @RequestMapping("sys/post")`, returns `Result<T>`, `@PreAuthorize("hasAuthority('sys:post:save')")`, `@OperateLog` on writes |
| Service | `service`, `service/impl` | `SysPostService`, `SysPostServiceImpl` | impl `extends BaseServiceImpl<Dao, Entity>` |
| DAO / Mapper | `dao` | `SysPostDao extends BaseDao<SysPostEntity>` | `@Mapper`; XML in `src/main/resources/mapper/**` for joins |
| Entity | `entity` | `SysPostEntity extends BaseEntity` | `@TableName("sys_post")` |
| VO | `vo` | `SysPostVO` | request/response body; may use `@Trans` |
| Query | `query` | `SysPostQuery extends Query` | page/limit/order/asc + filters |
| Convert | (Hutool `BeanUtil.copyProperties`) | — | no MapStruct in this repo |
| Enums | `enums` | `DataScopeEnum`, `UserStatusEnum` | |

## 4. Request lifecycle (backend)

```
HTTP request (Authorization: <uuid>)  or  ?access_token=<uuid>
  │
  ├─ CorsFilter / XssFilter (maku.xss.enabled, excludes /maku-generator/**)
  ├─ AuthenticationTokenFilter (OncePerRequestFilter, before UsernamePasswordAuthenticationFilter)
  │     token → Redis GET "sys:token:<uuid>" → UserDetail (authorities + dataScopeList)
  │     found? → SecurityContextHolder.setContext(...)      not found? → continue anonymous
  ├─ SecurityFilterChain: permitAll for auth.yml ignore_urls + OPTIONS; everything else authenticated()
  │     anonymous on protected URL → SecurityAuthenticationEntryPoint writes {"code":401,...} (HTTP 200!)
  ├─ Controller method
  │     @PreAuthorize("hasAuthority('x:y:z')")  → AccessDeniedException → {"code":403,...}
  │     @OperateLog → OperateLogAspect pushes a log DTO onto Redis list "sys:log"
  ├─ Service (BaseServiceImpl) → DAO (MyBatis-Plus)
  │     MybatisPlusInterceptor: DataScope → Pagination → OptimisticLock → BlockAttack
  │     FieldMetaObjectHandler fills creator/createTime/updater/updateTime/version/deleted/orgId
  ├─ Return Result.ok(data)
  │     TransResponseBodyAdvice fills @Trans fields (dict labels, org names) on the way out
  └─ Exceptions → ServerExceptionHandler → Result.error(code, msg)
```

Public URLs are declared in **`auth.yml`** files (any module can contribute one; `PermitResource` merges all `classpath*:auth.yml`): login/captcha/SMS/third-party endpoints, `/upload/**`, Swagger, actuator.

## 5. Response contract

Every JSON endpoint returns `net.maku.framework.common.utils.Result<T>`:

```json
{ "code": 0, "msg": "success", "data": { ... } }
```

| `code` | Meaning | Frontend reaction (`src/utils/request.ts`) |
|---|---|---|
| 0 | success | resolve with the body |
| 400 | refresh token invalid/expired | "Login timed out" dialog → reload to login |
| 401 | not logged in / access token expired | try `POST /sys/auth/token?refreshToken=…`, replay queued requests |
| 403 | missing permission | toast `msg` |
| 500 / other | business error (`ServerException`) or unexpected exception | toast `msg` |

Paged lists return `PageResult<T>` = `{ list: [...], total: n }`.

## 6. Cross-cutting building blocks (reuse these!)

| Need | Use | Location |
|---|---|---|
| Current user | `SecurityUser.getUser() / getUserId() / getOrgId()` | `maku-framework/.../security/user/SecurityUser.java` |
| Business error | `throw new ServerException("msg")` | `.../common/exception` |
| Paging | `Query` subclass + `getPage(query)` + `PageResult` | `BaseServiceImpl` |
| Row-level security | `dataScopeWrapper(wrapper)` or `params.put(Constant.DATA_SCOPE, getDataScope("t1", null))` | `BaseServiceImpl` |
| Audit log | `@OperateLog(type = OperateTypeEnum.INSERT)` | `.../operatelog` |
| Dict / FK label translation | `@Trans(type = TransType.DICTIONARY, key = "user_status", ref = "statusLabel")` | `.../common/trans` |
| Redis | `RedisCache` (+ key helpers in `RedisKeys`) | `.../common/cache` |
| Files | inject `StorageService` (or `StorageApi` from other modules) | `maku-boot-system/.../storage` |
| SMS | `SmsApi.sendCode(mobile, key, value)` | `maku-boot-api` |
| Excel | `ExcelUtils` | `.../common/utils` |
| Tree data | `TreeNode` + `TreeUtils.build(list)` | `.../common/utils` |

## 7. Frontend architecture

See [04 — Frontend](04-frontend.md). In one line: `views/**/index.vue` pages are mounted by **routes generated at runtime from the backend menu tree**, call the API via one axios instance, and use the `useCrud` hook for list pages.
