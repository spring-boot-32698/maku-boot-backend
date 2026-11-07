# Onboarding docs — maku-boot + maku-admin

Written from the source code on 2026-10-11. Where these docs and the code disagree, **the code wins**; please fix the doc.

| # | Doc | Read it when… |
|---|-----|---------------|
| 01 | [Tech stack](01-tech-stack.md) | Day 1. What libraries/versions we use and why. |
| 02 | [Architecture](02-architecture.md) | Day 1. Maven modules, packages, request lifecycle, layering conventions. |
| 03 | [Business flows](03-business-flows.md) | Day 1–2. Login, tokens, RBAC/menus, data scope, users/orgs, dictionaries, files, SMS/email, logs, jobs, WebSocket. |
| 04 | [Frontend](04-frontend.md) | Day 2. Vue app structure, dynamic routing, axios, `useCrud`, `v-auth`. |
| 05 | [Database](05-database.md) | Day 2. Tables, conventions (logic delete, version, audit columns), seed data. |
| 06 | [Local setup](06-local-setup.md) | Before you run anything. |
| 07 | [Recipe: add a CRUD feature](07-add-a-feature.md) | Your first ticket. |
| 08 | [Gotchas & known issues](08-gotchas.md) | Before your first PR. |
| 09 | [Login accounts](09-accounts.md) | Logging in to try things: admin + demo users with different roles. |

Interactive lessons live in [`../lessons/`](../lessons/) (open `../lessons/index.html` in a browser).
Quick-reference sheets live in [`../reference/`](../reference/).

## The one-paragraph summary

**maku-boot** is a Spring Boot 4 / Java 17 monolith (a "low-code admin platform") built from Maven modules. Spring Security authenticates every request using an **opaque access token** (a UUID) sent in the `Authorization` header; the token maps to a cached `UserDetail` in **Redis** that carries the user's **permission codes** (e.g. `sys:user:save`) and **data scope** (which org IDs they can see). Persistence is **MyBatis-Plus** on **MySQL**, with soft-delete, optimistic locking, audit-column auto-fill and a custom data-scope SQL interceptor. **maku-admin** is a Vue 3 + TypeScript + Element Plus SPA: after login it downloads the user's menu tree and permission list, generates routes dynamically from `sys_menu.url`, and hides buttons with the `v-auth` directive.
