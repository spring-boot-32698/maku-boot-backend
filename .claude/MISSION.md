# Mission: Become productive on the maku-boot + maku-admin codebase

## Why
I'm a new joiner on this project. I need to understand the tech stack and the business flows well enough to pick up tickets, find the right file quickly, and ship changes to the backend (`maku-boot-backend`) and the admin UI (`maku-admin-frontend`) without breaking auth, permissions or data scope.

## Success looks like
- I can run the full stack locally (MySQL + Redis + backend + frontend) and log in as `admin`.
- I can trace any request from a Vue page → axios → Spring Security filter → controller → service → MyBatis-Plus → MySQL and back.
- I can explain the login/token/refresh flow and where tokens live (Redis + `sys_user_token`).
- I can add a new CRUD feature end-to-end: table, entity/dao/service/controller, `@PreAuthorize` codes, `sys_menu` rows, API file, Vue page with `v-auth`.
- I know which "platform services" exist (dictionary + `@Trans`, storage, SMS/email, operate log, Quartz jobs, WebSocket) and reuse them instead of reinventing.

## Constraints
- Learning on the job: lessons must be short (≈10 minutes) and tied to real files in this repo.
- Source of truth is the code in this repo (Spring Boot 4.0.6, Java 17, Vue 3.5); upstream maku docs may describe a different version.

## Out of scope (for now)
- The commercial `maku-generator-pro` internals, the IoT module (not wired into `maku-server`), multi-tenancy and Flowable (enterprise-edition features mentioned in the README but not present in this source).
