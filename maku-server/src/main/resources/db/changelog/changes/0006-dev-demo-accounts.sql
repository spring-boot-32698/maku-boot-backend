--liquibase formatted sql

-- Demo organisations, roles and users for local development.
-- context:dev => only applied when spring.liquibase.contexts includes "dev" (the dev profile).
-- All demo users have password 123456 (SM3: 207cf410…d018fb). Documented in .claude/docs/09-accounts.md.

--changeset maku:0006-dev-demo-accounts context:dev
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(*) from sys_user where username in ('manager','staff','auditor','disabled')

-- Org tree: HQ (101) -> Sales (102) -> Sales North (103); HQ (101) -> Operations (104)
INSERT INTO sys_org (id, pid, name, sort, leader_id, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES
 (101, NULL, 'HQ',          0, NULL, 10000, 0, 0, 10000, now(), 10000, now()),
 (102, 101,  'Sales',       0, NULL, 10000, 0, 0, 10000, now(), 10000, now()),
 (103, 102,  'Sales North', 0, NULL, 10000, 0, 0, 10000, now(), 10000, now()),
 (104, 101,  'Operations',  1, NULL, 10000, 0, 0, 10000, now(), 10000, now());

-- Roles. data_scope: 0 ALL, 1 ORG_AND_CHILD, 2 ORG_ONLY, 3 SELF, 4 CUSTOM
INSERT INTO sys_role (id, name, role_code, remark, data_scope, org_id, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES
 (101, 'HR Manager', 'HR_MANAGER', 'Full Access Management; sees own org and sub-orgs', 1, 101, 10000, 0, 0, 10000, now(), 10000, now()),
 (102, 'Staff',      'STAFF',      'Read-only Access Management; sees only own rows',   3, 101, 10000, 0, 0, 10000, now(), 10000, now()),
 (103, 'Auditor',    'AUDITOR',    'Logs and monitoring; sees all data',                0, 101, 10000, 0, 0, 10000, now(), 10000, now());

-- HR Manager: Access Management (12) with every page and button
INSERT INTO sys_role_menu (role_id, menu_id, version, deleted, creator, create_time, updater, update_time)
SELECT 101, id, 0, 0, 10000, now(), 10000, now() FROM sys_menu
 WHERE deleted = 0 AND (id = 12 OR pid = 12 OR pid IN (SELECT id FROM (SELECT id FROM sys_menu WHERE pid = 12) t));

-- Staff: Access Management pages with Query buttons only (Posts, Orgs, Users)
INSERT INTO sys_role_menu (role_id, menu_id, version, deleted, creator, create_time, updater, update_time)
SELECT 102, id, 0, 0, 10000, now(), 10000, now() FROM sys_menu
 WHERE deleted = 0 AND (id = 12 OR url IN ('sys/post/index', 'sys/org/index', 'sys/user/index')
       OR authority IN ('sys:post:page', 'sys:org:list', 'sys:user:page'));

-- Auditor: Log Management + System monitoring (menus looked up by url, ids differ per module script order)
INSERT INTO sys_role_menu (role_id, menu_id, version, deleted, creator, create_time, updater, update_time)
SELECT 103, id, 0, 0, 10000, now(), 10000, now() FROM sys_menu
 WHERE deleted = 0 AND (id IN (33, 34)
       OR url IN ('sys/log/login', 'sys/log/operate', 'monitor/server/index', 'monitor/cache/index', 'monitor/user/index')
       OR (pid = 33 AND name = 'System monitoring'));

-- Users (password 123456). status: 1 normal, 0 disabled
INSERT INTO sys_user (id, username, password, real_name, avatar, gender, email, mobile, status, org_id, super_admin, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES
 (10001, 'manager',  '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', 'Mia Manager',  NULL, 1, 'manager@example.com',  '13800000001', 1, 102, 0, 10000, 0, 0, 10000, now(), 10000, now()),
 (10002, 'staff',    '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', 'Sam Staff',    NULL, 0, 'staff@example.com',    '13800000002', 1, 103, 0, 10000, 0, 0, 10000, now(), 10000, now()),
 (10003, 'auditor',  '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', 'Ada Auditor',  NULL, 1, 'auditor@example.com',  '13800000003', 1, 101, 0, 10000, 0, 0, 10000, now(), 10000, now()),
 (10004, 'disabled', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', 'Dan Disabled', NULL, 0, 'disabled@example.com', '13800000004', 0, 104, 0, 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_user_role (role_id, user_id, version, deleted, creator, create_time, updater, update_time) VALUES
 (101, 10001, 0, 0, 10000, now(), 10000, now()),
 (102, 10002, 0, 0, 10000, now(), 10000, now()),
 (103, 10003, 0, 0, 10000, now(), 10000, now()),
 (102, 10004, 0, 0, 10000, now(), 10000, now());

--rollback DELETE FROM sys_user_role WHERE user_id IN (10001, 10002, 10003, 10004);
--rollback DELETE FROM sys_user WHERE id IN (10001, 10002, 10003, 10004);
--rollback DELETE FROM sys_role_menu WHERE role_id IN (101, 102, 103);
--rollback DELETE FROM sys_role WHERE id IN (101, 102, 103);
--rollback DELETE FROM sys_org WHERE id IN (101, 102, 103, 104);
