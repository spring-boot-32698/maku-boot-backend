# 05 — Database

Schema and seed data are managed by **Liquibase**, which runs when the app starts:

- Master changelog: `maku-server/src/main/resources/db/changelog/db.changelog-master.yaml`.
- **Baseline** = the scripts in `db/mysql-ver-english/` (`maku.sql` + `module/{generator,member,monitor,quartz}.sql`). `maku-server/pom.xml` packages them into the jar as `classpath:db/init/**` (the iot script is excluded). Each script is one changeset (`0001`–`0005`).
- Each baseline changeset has a precondition (`onFail: MARK_RAN`), so a database that was already created from these scripts is adopted rather than re-created.
- **New changes go in new files** under `db/changelog/changes/` (picked up by `includeAll` in file-name order). Never edit `db/mysql-ver-english/*.sql` or an applied changeset: Liquibase checksums will fail at startup.
- Applied changesets are recorded in the `DATABASECHANGELOG` table.
- `db/mysql/` is the original Chinese-comment version and is not used.

Schema: `maku_boot`, user `maku` / `123456` (dev). MySQL 8, `utf8mb4`.

## Conventions every business table follows

Inherited from `BaseEntity` (`maku-framework/.../mybatis/entity/BaseEntity.java`):

| Column | Type | Filled by | Purpose |
|---|---|---|---|
| `id` | bigint | DB `AUTO_INCREMENT` (`id-type: AUTO`) | PK |
| `creator` | bigint | `FieldMetaObjectHandler` on insert | user id; used by data scope `creator = me` |
| `create_time` | datetime | on insert | |
| `updater` | bigint | on insert & update | |
| `update_time` | datetime | on insert & update | |
| `version` | int | 0 on insert | optimistic lock (`@Version`) — an `updateById` with a stale version updates 0 rows |
| `deleted` | tinyint | 0 on insert | **logic delete** (`@TableLogic`): `removeById` sets `deleted=1`; all MP queries add `deleted=0` |
| `org_id` | bigint | on insert (if the entity has the field) | data scope by organisation |
| `tenant_id` | bigint | — | present on sys tables; multi-tenancy is **not implemented** in this edition |

Hand-written XML queries **must add `deleted = 0` themselves** (MP only auto-adds it for wrapper/BaseMapper queries) — see `SysRoleDao.xml` for the pattern.

## Core tables (`maku.sql`)

| Area | Tables |
|---|---|
| Identity | `sys_user`, `sys_user_token`, `sys_third_login`, `sys_third_login_config` |
| Organisation | `sys_org` (tree via `pid`), `sys_post`, `sys_user_post` |
| RBAC | `sys_role`, `sys_user_role`, `sys_menu`, `sys_role_menu`, `sys_role_data_scope` |
| Config | `sys_dict_type`, `sys_dict_data`, `sys_params` |
| Files | `sys_attachment` |
| Audit | `sys_log_login`, `sys_log_operate` |
| Messaging | `sys_sms_config`, `sys_sms_log`, `sys_mail_config`, `sys_mail_log` |

## Module tables

| Module | Tables |
|---|---|
| quartz | `schedule_job`, `schedule_job_log` (Quartz itself uses the RAM job store — no `QRTZ_*` tables) |
| member | `member_user` |
| generator | `gen_datasource`, `gen_config`, `gen_field_type`, `gen_base_class`, `gen_table`, `gen_table_field`, `gen_project_modify`, `gen_test_*` (sample tables) |
| iot (inactive) | `iot_device`, `iot_device_event_log`, `iot_device_service_log` |

## Seed data worth knowing

- **User** `admin` (id 10000), password `admin` (stored as SM3 hex `dc1fd00e…76ae6`), `super_admin = 1`.
- **Menus**: 52 `sys_menu` rows in the core script (more in module scripts). Example: id 2 "Menu Management" url `sys/menu/index`, children are buttons with codes `sys:menu:list`, `sys:menu:save`, `sys:menu:update,sys:menu:info`, `sys:menu:delete`.
- **Dictionaries**: `post_status`, `user_gender`, `user_status`, `role_data_scope`, `enable_disable`, `success_fail`, `login_operation`, `params_type`, `user_super_admin`, `log_operate_type`, `sms_platform`, `mail_platform`, …
- **Params**: `LOGIN_CAPTCHA = false` (captcha off in dev).
- No roles or orgs are in the baseline. In the **dev** profile, changeset `0006-dev-demo-accounts` (context `dev`) adds an org tree, 3 roles and 4 demo users. See [09 — Login accounts](09-accounts.md).

## Useful queries while debugging

```sql
-- What can user X do?
select m.authority from sys_menu m
 join sys_role_menu rm on rm.menu_id = m.id and rm.deleted = 0
 join sys_user_role ur on ur.role_id = rm.role_id and ur.deleted = 0
where ur.user_id = 10000 and m.deleted = 0 and m.authority <> '';

-- Effective data scope (min wins)
select min(r.data_scope) from sys_role r join sys_user_role ur on ur.role_id = r.id
where ur.user_id = ? and r.deleted = 0 and ur.deleted = 0;

-- Token state
select * from sys_user_token where user_id = 10000;
```

```bash
# Redis: who is logged in?
redis-cli -n 1 --scan --pattern 'sys:token:*'
```
(Dev Redis uses database **1**: `spring.data.redis.database: 1`.)
