# 04 — Frontend (`maku-admin-frontend`)

## Folder map

```
src/
├── main.ts            createApp → Pinia → v-auth directive → i18n → router → Ma* components → Element Plus
├── App.vue
├── api/               one file per backend resource; thin wrappers around the axios instance
│   ├── auth.ts        /sys/auth/* (captcha, login, mobile, third, logout)
│   ├── sys/           user, role, menu, orgs, post, dict, params, attachment, upload, sms, mail, third
│   ├── quartz/ monitor/ member/ iot/
├── views/             pages; a menu url "sys/user/index" ⇒ views/sys/user/index.vue
│   ├── login/         account.vue · mobile.vue · third.vue
│   ├── sys/ quartz/ monitor/ member/ iot/ profile/ dashboard/ demo/
├── layout/            shell: Navbar, Menu, Tabs, Settings, Theme, Router/{Redirect,Iframe}.vue
├── router/index.ts    static routes + beforeEach guard + generateRoutes(menuTree)
├── store/modules/     user.ts (token, user, authorityList) · router.ts (menuRoutes) · app.ts (dictList, theme…) · tabs.ts
├── hooks/index.ts     useCrud(): list/page/sort/delete/export for list pages
├── components/        global Ma* components: ma-dict-*, ma-data-*, ma-upload-*, ma-org-*, ma-user-*, ma-editor, ma-markdown…
├── utils/
│   ├── request.ts     axios instance + interceptors (token header, 401 → refresh, error toasts)
│   ├── cache/         localStorage keys: maToken, maRefreshToken, lang, theme, sidebarOpened…
│   ├── directive/     v-auth
│   ├── smCrypto.ts    SM2 encryption of the login password
│   └── constant.ts, tool.ts, validate.ts, theme.ts, tabs.ts
├── i18n/              vue-i18n messages (lang/*)
├── icons/ styles/ assets/ types/
```

## Boot sequence after login (`src/router/index.ts`)

1. `beforeEach`: token present but `userStore.user.id` empty ⇒
   `getUserInfoAction()` (`GET /sys/user/info`) → `getAuthorityListAction()` (`GET /sys/menu/authority`) → `appStore.getDictListAction()` (`GET /sys/dict/type/all`).
2. `routerStore.getMenuRoutes()`: `GET /sys/menu/nav` → `generateRoutes()`:
   - menu with children → path `/p/<id>`, component = layout;
   - leaf with internal url → path `/<url>`, component = `import.meta.glob('/src/views/**/*.vue')[`/src/views/${url}.vue`]`;
   - leaf with external url → `/iframe/<id>` (or new window if `openStyle === 1`).
   - Frontend-only menus are appended: `dashboardMenu` (Home) and `constantMenu` (Demo pages).
3. Routes are **flattened** (`getKeepAliveRoutes`) under the single layout route `/`, breadcrumbs computed, `router.addRoute(...)`, then a 404 catch-all.
4. No token ⇒ only `/login` allowed; others redirect to `/login?redirect=…`.

> **If you create a page and it shows 404**, the cause is almost always the menu: no `sys_menu` row, wrong `url` (must match the file path under `src/views` without `.vue`), or the role lacks the menu.

## HTTP layer (`src/utils/request.ts`)

- `baseURL = import.meta.env.VITE_API_URL`, 60 s timeout, JSON by default.
- Request: `Authorization: <token>` (raw, no "Bearer"), `Accept-Language`, `t=<timestamp>` on GETs (cache-busting), form-encode if content-type says so.
- Response: `code 0` ⇒ resolve `res` (so callers use `res.data`); blobs pass through; `401` ⇒ refresh-and-replay; `400` ⇒ force re-login; anything else ⇒ `ElMessage.error(res.msg)` and reject.

## Page patterns

**List page** (`views/sys/post/index.vue`):

```ts
const state: IHooksOptions = reactive({
  dataListUrl: '/sys/post/page',   // GET with page, limit, order, asc + queryForm
  deleteUrl: '/sys/post',          // DELETE with body [ids]
  queryForm: { postCode: '', postName: '', status: '' }
})
const { getDataList, selectionChangeHandle, sizeChangeHandle,
        currentChangeHandle, deleteBatchHandle, reset } = useCrud(state)
```

Template: query `el-form` → buttons guarded by `v-auth="'sys:post:save'"` → `el-table` bound to `state.dataList` → `el-pagination` → `<add-or-update ref=… @refresh-data-list="getDataList">`.

**Add/Edit dialog** (`add-or-update.vue`): `init(id?)` opens the dialog, resets the form, loads `GET /sys/post/{id}` if editing; `submitHandle` validates and calls `usePostSubmitApi` (POST if no id, PUT otherwise), then emits `refreshDataList`. Exposed via `defineExpose({ init })`.

**API file** (`api/sys/post.ts`): `usePostApi(id)`, `usePostSubmitApi(form)`, `usePostListApi()`… naming convention `use<Thing>Api`.

## Dictionaries in the UI

`<ma-dict-select v-model="form.status" dict-type="post_status" />`, `<ma-dict-column prop="status" dict-type="post_status" />`, `<ma-dict-label>`, `<ma-dict-radio>`. All read from `appStore.dictList` loaded at boot — **a newly added dictionary needs a page reload**.

## Conventions

- `<script setup lang="ts" name="SysPostIndex">` — `name` (via `vite-plugin-vue-setup-extend`) is used for keep-alive caching; keep it unique.
- Prettier: tabs, single quotes, no semicolons, width per `.prettierrc.cjs`. Run `npm run lint`.
- SVG icons live in `src/icons/svg` → `icon-<name>`; menu icons use the iconfont names like `icon-setting`.

## Build & deploy

`npm run build` (= `vue-tsc --noEmit && vite build`) → `dist/`. Docker: Node 22 build stage with `VITE_API_URL` build-arg → nginx serving SPA with `try_files … /index.html`.
