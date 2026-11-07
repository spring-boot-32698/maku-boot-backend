# maku-boot / maku-admin Resources

## Knowledge

### In this repo (highest trust — it *is* the system)
- `docs/` in this folder — the onboarding docs written from the source code. Use for: everything first.
- `maku-boot-backend/db/mysql-ver-english/maku.sql` — schema + seed data (menus, roles, dictionaries, `admin` user). Use for: "what tables/menus/permissions exist?"
- `maku-boot-backend/maku-server/src/main/resources/application*.yml` — runtime config. Use for: ports, Redis/DB, token lifetimes, storage type.

### Upstream project docs
- [maku-boot documentation (maku.net)](https://maku.net/docs/maku-boot) — official guide from the project author. Use for: feature overviews, deployment, code generator usage. Caveat: may target a different version than this repo.
- [maku-admin documentation (maku.net)](https://maku.net/docs/maku-admin) — frontend guide. Use for: layout, components, theming.
- [maku-boot on GitHub](https://github.com/makunet/maku-boot) / [maku-admin on GitHub](https://github.com/makunet/maku-admin) — upstream source + issues. Use for: diffing against upstream, finding known bugs.

### Framework primary sources
- [Spring Security — Servlet Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html) — filter chain, `SecurityContextHolder`. Use for: understanding `AuthenticationTokenFilter` and `SecurityFilterConfig`.
- [Spring Security — Authentication architecture](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html) — `AuthenticationManager`, `ProviderManager`, `AuthenticationProvider`. Use for: the three login providers (password / mobile / third-party).
- [Spring Security — Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html) — `@PreAuthorize("hasAuthority(...)")`. Use for: permission codes on controllers.
- [MyBatis-Plus docs (baomidou.com)](https://baomidou.com/en/) — `BaseMapper`, `ServiceImpl`, wrappers, plugins (pagination, optimistic lock, logic delete, auto-fill). Use for: anything in `maku-framework/.../mybatis`.
- [Spring Boot reference](https://docs.spring.io/spring-boot/reference/) — config, profiles, Redis, Quartz, WebSocket starters.
- [Vue Router — Navigation Guards](https://router.vuejs.org/guide/advanced/navigation-guards.html) and [Dynamic Routing](https://router.vuejs.org/guide/advanced/dynamic-routing.html) — `beforeEach` + `addRoute`. Use for: `src/router/index.ts`.
- [Pinia docs](https://pinia.vuejs.org/core-concepts/) — stores. Use for: `src/store/modules/*`.
- [Element Plus components](https://element-plus.org/en-US/component/overview.html) — tables, forms, dialogs used on every page.
- [Axios interceptors](https://axios-http.com/docs/interceptors) — Use for: `src/utils/request.ts` (token header, refresh-token retry).
- [Quartz Scheduler tutorials](https://www.quartz-scheduler.org/documentation/) — Use for: `maku-module-quartz`.

## Wisdom (Communities)
- Your own team — code review on your first PRs is the highest-signal feedback for *this* codebase. Ask who owns which module.
- [maku-boot GitHub Issues](https://github.com/makunet/maku-boot/issues) / [Gitee issues](https://gitee.com/makunet/maku-boot/issues) — upstream maintainers and users (mostly Chinese-language).
- [Stack Overflow `spring-security` tag](https://stackoverflow.com/questions/tagged/spring-security) and [`mybatis-plus` tag](https://stackoverflow.com/questions/tagged/mybatis-plus) — framework-level questions.

## Gaps
- No English-language, version-matched guide for maku-boot 5.x on Spring Boot 4 exists; `docs/` here fills that gap — keep it updated.
- No automated tests in the repo (`skipTests=true` in the root `pom.xml`); there is no test-suite to learn behaviour from.
