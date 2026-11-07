INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (1, 'Scheduled tasks', 'quartz/schedule/index', NULL, 0, 0, 'icon-reloadtime', 0, 0, 0, 10000, now(), 10000, now());

SET @menuId = @@identity;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'Check', '', 'schedule:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'New', '', 'schedule:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'Revise', '', 'schedule:update,schedule:info', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'Delete', '', 'schedule:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'Run immediately', '', 'schedule:run', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @menuId), 'Log', '', 'schedule:log', 1, 0, '', 4, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ('schedule_group', 'Task group name', 'Scheduled tasks', 0, 10000, 0, 0, 10000, now(), 10000, now());

SET @typeId = @@identity;
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'default', 'default', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'system', 'system', '', '', 1, 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ('schedule_status', 'state', 'Scheduled tasks', 0, 10000, 0, 0, 10000, now(), 10000, now());

SET @typeId = @@identity;
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'pause', '0', 'danger', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'normal', '1', 'primary', '', 1, 10000, 0, 0, 10000, now(), 10000, now());


DROP TABLE IF EXISTS schedule_job;
DROP TABLE IF EXISTS schedule_job_log;

CREATE TABLE schedule_job (
    id              bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    job_name        varchar(200) COMMENT 'name',
    job_group       varchar(100) COMMENT 'Grouping',
    bean_name       varchar(200)  COMMENT 'Spring bean name',
    method          varchar(100) COMMENT 'Execution method',
    params          varchar(2000) COMMENT 'parameter',
    cron_expression varchar(100) COMMENT 'cron expression',
    status          tinyint unsigned COMMENT 'Status 0: Paused 1: Normal',
    concurrent      tinyint unsigned COMMENT 'Concurrency: 0: Disabled; 1: Enabled',
    remark          varchar(255) COMMENT 'Remark',
    version         int COMMENT 'Version number',
    deleted         tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator         bigint COMMENT 'Creator',
    create_time     datetime COMMENT 'Creation time',
    updater         bigint COMMENT 'Updater',
    update_time     datetime COMMENT 'Update time',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='Scheduled tasks';

CREATE TABLE schedule_job_log (
    id            bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    job_id        bigint NOT NULL COMMENT 'Task ID',
    job_name      varchar(200) COMMENT 'Task Name',
    job_group     varchar(100) COMMENT 'Task group name',
    bean_name     varchar(200) COMMENT 'Spring bean name',
    method        varchar(100) COMMENT 'Execution method',
    params        varchar(2000) COMMENT 'parameter',
    status        tinyint unsigned NOT NULL COMMENT 'Task status: 0: Failure; 1: Success',
    error         varchar(2000) COMMENT 'Abnormal information',
    times         bigint NOT NULL COMMENT 'Time elapsed (in milliseconds)',
    create_time   datetime COMMENT 'Creation time',
    PRIMARY KEY (id),
    key idx_job_id (job_id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='Scheduled task logs';


INSERT INTO schedule_job (id, job_name, job_group, bean_name, method, params, cron_expression, status, concurrent, remark, version, deleted, creator, create_time, updater, update_time) VALUES (1, 'Test task', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 14, 0, 10000, now(), 10000, now());
