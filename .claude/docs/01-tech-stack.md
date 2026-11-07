# 01 — Tech stack

Versions are taken from `maku-boot-backend/pom.xml` and `maku-admin-frontend/package.json`.

## Backend (`maku-boot-backend`)

| Concern | Technology | Version | Where you'll meet it |
|---|---|---|---|
| Language / runtime | Java | 17 | `pom.xml` `<java.version>` |
| Framework | Spring Boot (parent) | **4.0.6** | root `pom.xml` |
| Web | Spring MVC + embedded Tomcat | via Boot | controllers in `*/controller` |
| Security | Spring Security (stateless, custom token filter) | via Boot (7.x) | `maku-framework/.../security`, `maku-boot-system/.../security` |
| ORM | MyBatis-Plus (`mybatis-plus-spring-boot4-starter`) + JSqlParser | 3.5.16 | `BaseDao`, `BaseServiceImpl`, `mapper/**/*.xml` |
| Datasources | dynamic-datasource (baomidou) + HikariCP | 4.5.0 | `spring.datasource.dynamic.*` in `application-dev.yml` |
| Database | MySQL 8 (driver `mysql-connector-j`) | 8.0 | `db/mysql-ver-english/*.sql` |
| Cache / token store | Redis via Spring Data Redis + **Redisson** | Redisson 4.1.0 | `RedisCache`, `TokenStoreCache` |
| Distributed lock | lock4j (Redisson) | 2.2.7 | available, `@Lock4j` |
| API docs | springdoc-openapi + **Knife4j** UI | 3.0.2 / 4.5.0 | `SwaggerConfig`, `/doc.html`, `/swagger-ui.html` |
| Validation | Jakarta Bean Validation (`spring-boot-starter-validation`) | via Boot | `@Valid`, `Query` |
| Utilities | Hutool | 5.8.28 | `BeanUtil`, `StrUtil`, `SmUtil`… everywhere |
| Excel import/export | FastExcel (EasyExcel fork) | 1.2.0 | `ExcelUtils`, user import/export |
| Crypto | BouncyCastle; **SM2** (login password transport) + **SM3** (password hashing) | 1.69 | `Sm2Util`, `Sm3PasswordEncoder` |
| Captcha | easy-captcha | 1.6.2 | `SysCaptchaServiceImpl` |
| Third-party login | JustAuth (WeCom, DingTalk, Feishu, WeChat…) | 1.16.6 | `SysThirdLoginController` |
| IP → region | ip2region | 2.7.0 | `IpUtils`, login/operate logs |
| Object storage | Local disk, Aliyun OSS, Tencent COS, Qiniu, Huawei OBS, MinIO | various | `maku-boot-system/.../storage` |
| SMS | Aliyun, Tencent, Qiniu, Huawei | various | `maku-boot-system/.../sms` (Strategy pattern) |
| Email | Aliyun DirectMail, Tencent SES, JavaMail SMTP | various | `maku-boot-system/.../email` |
| Scheduling | Quartz (`spring-boot-starter-quartz`) | via Boot | `maku-module-quartz` |
| Realtime | Spring WebSocket (raw, not STOMP) | via Boot | `maku-module-websocket`, endpoint `/ws` |
| Server monitoring | OSHI | — | `maku-module-monitor` |
| Code generator | `maku-generator-pro-boot-starter` (closed jar) | 2.0.0 | `maku-module-generator`, UI under `/maku-generator/**` |
| IoT (not active) | Spring Integration MQTT, Netty-style TCP | 7.0.3 | `maku-module-iot` — **commented out** in `maku-server/pom.xml` |
| Boilerplate | Lombok | via Boot | `@Data`, `@AllArgsConstructor` (constructor injection) |
| Build | Maven (multi-module, `${revision}` = 5.0.0), Aliyun mirror | — | `pom.xml`, `maven-settings.xml` |
| Packaging | Docker multi-stage (Maven 3.9 + Temurin 17 JRE) | — | `Dockerfile`, `docker-compose.yml` |

**Tests:** `skipTests=true` in the root POM; there is effectively no test suite.

## Frontend (`maku-admin-frontend`)

| Concern | Technology | Version |
|---|---|---|
| Framework | Vue 3 (`<script setup lang="ts">`) | 3.5.33 |
| Language | TypeScript | 5.7 |
| Build / dev server | Vite (port **3000**) | 7.3.2 |
| UI kit | Element Plus + `@element-plus/icons-vue` | 2.13.7 |
| Routing | Vue Router (HTML5 history, **dynamic routes from backend menus**) | 4.6.4 |
| State | Pinia | 2.3.1 |
| HTTP | Axios + `qs` | 1.15.2 |
| i18n | vue-i18n | 11.1.2 |
| Crypto | `sm-crypto` (SM2 encrypt password before login) | 0.3.x |
| Rich text / markdown | wangEditor, v-md-editor | — |
| Charts | ECharts | 5.5 |
| Misc | nprogress, mitt (event bus), cropperjs, print-js, qrcode.vue, sortablejs, @vueuse/core | — |
| Lint/format | ESLint 8 + Prettier 3 (tabs, single quotes, no semicolons) | — |
| Deploy | Dockerfile + nginx (`nginx.conf`) | — |

API base URL comes from `VITE_API_URL` (`.env.development` → `http://localhost:8080`).

## Why these choices (as far as the code tells us)

- **Opaque token + Redis, not JWT**: lets the server revoke sessions and *push permission changes into live sessions* (`SysUserTokenServiceImpl#updateCacheAuthByRoleId`). See [03 — Business flows](03-business-flows.md#2-tokens-access-refresh-logout).
- **SM2/SM3**: Chinese national crypto standards ("Xinchuang" compliance, per the README).
- **Strategy pattern for SMS/storage/email**: swap vendors by config/DB row rather than code.
- **MyBatis-Plus over JPA**: lambda wrappers for simple queries, hand-written XML for joins; a SQL interceptor injects data-scope filters.
