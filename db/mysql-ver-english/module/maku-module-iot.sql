-- Table Structure
CREATE TABLE iot_device (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  code varchar(255) NOT NULL COMMENT 'coding',
  name varchar(255) NOT NULL COMMENT 'name',
  type int NOT NULL COMMENT 'Equipment types: 1. Handheld devices, 2. Cabinet-mounted devices, 3. Sensor devices',
  uid varchar(255) NOT NULL COMMENT 'Unique Identifier',
  secret varchar(255)  DEFAULT NULL COMMENT 'Device key',
  app_version varchar(255) DEFAULT NULL COMMENT 'App version number',
  battery_percent varchar(10)  DEFAULT NULL COMMENT 'Battery percentage',
  temperature varchar(10)  DEFAULT NULL COMMENT 'temperature',
  status tinyint NOT NULL DEFAULT '1' COMMENT 'Status: 0 Disabled, 1 Enabled',
  running_status int NOT NULL DEFAULT '0' COMMENT 'Running status: 0. Offline; 1. Online; 2. Normal standby; 3. In use; 4. OTA upgrade in progress.',
  protocol_type varchar(20)  NOT NULL DEFAULT 'MQTT' COMMENT 'Protocol type',
  up_time datetime DEFAULT NULL COMMENT 'Launch time',
  down_time datetime DEFAULT NULL COMMENT 'Offline time',
  tenant_id bigint DEFAULT NULL COMMENT 'Tenant ID',
  creator bigint DEFAULT NULL COMMENT 'Creator',
  create_time datetime DEFAULT NULL COMMENT 'Creation time',
  updater bigint DEFAULT NULL COMMENT 'Updater',
  update_time datetime DEFAULT NULL COMMENT 'Update time',
  version int DEFAULT NULL COMMENT 'Version number',
  deleted tinyint DEFAULT NULL COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='Equipment list';

CREATE TABLE iot_device_event_log (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  device_id bigint NOT NULL COMMENT 'Device ID',
  event_type tinyint NOT NULL COMMENT 'Event Type',
  event_uid varchar(50) DEFAULT NULL COMMENT 'Event Identifier ID',
  event_payload varchar(1000) DEFAULT NULL COMMENT 'Event Data',
  event_time datetime DEFAULT NULL COMMENT 'Event Time',
  tenant_id bigint DEFAULT NULL COMMENT 'Tenant ID',
  version int DEFAULT NULL COMMENT 'Version number',
  deleted tinyint DEFAULT NULL COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
  creator bigint DEFAULT NULL COMMENT 'Creator',
  create_time datetime DEFAULT NULL COMMENT 'Creation time',
  updater bigint DEFAULT NULL COMMENT 'Updater',
  update_time datetime DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='Device event log';

CREATE TABLE iot_device_service_log (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  device_id bigint NOT NULL COMMENT 'Device ID',
  service_type tinyint NOT NULL COMMENT 'Service type',
  service_uid varchar(50) DEFAULT NULL COMMENT 'Service ID',
  service_payload varchar(1000) DEFAULT NULL COMMENT 'Service data',
  service_time datetime DEFAULT NULL COMMENT 'Service Hours',
  tenant_id bigint DEFAULT NULL COMMENT 'Tenant ID',
  version int DEFAULT NULL COMMENT 'Version number',
  deleted tinyint DEFAULT NULL COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
  creator bigint DEFAULT NULL COMMENT 'Creator',
  create_time datetime DEFAULT NULL COMMENT 'Creation time',
  updater bigint DEFAULT NULL COMMENT 'Updater',
  update_time datetime DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='Device service log';


INSERT INTO iot_device (id, code, name, type, uid, secret, app_version, battery_percent, temperature, status, running_status, protocol_type, up_time, down_time, tenant_id, creator, create_time, updater, update_time, version, deleted) VALUES (1, 'test-tcp', 'testTCP', 1, 'test12345678', '123456', NULL, NULL, NULL, 1, 1, 'TCP', now(), NULL, NULL, 10000, now(), 10000, now(), 0, 0);

-- Menu, Permissions
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ( NULL, 'Internet of Things Platform', NULL, NULL, 0, 0, 'icon-printer-fill', 6, 0, 0, 10000,now(), 10000, now());

SET @pid = @@identity;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @pid) , 'Equipment List', 'iot/device/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 10000, now(), 10000, now());

SET @menuId = @@identity;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Check', '', 'iot:device:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'New', '', 'iot:device:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Revise', '', 'iot:device:update,iot:device:info', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'delete', '', 'iot:device:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Issue instructions', '', 'iot:device:send', 1, 0, '', 4, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Reported data', '', 'iot:device:report', 1, 0, '', 5, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Device event log', '', 'iot:device_event_log:page', 1, 0, '', 5, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (@menuId, 'Device service log', '', 'iot:device_service_log:page', 1, 0, '', 5, 0, 0, 10000, now(), 10000, now());

-- 字典数据
INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUE('device_type', 'Equipment type', 'Equipment type', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'handheld devices', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'cabinet', '2', 'primary', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Sensing devices', '3', 'primary', '', 2, 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUES('device_running_status', 'Equipment operating status', 'Device operating status: Offline | Online | Standby | In use | OTA upgrade in progress', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Offline status', '0', 'danger', NULL, 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Online status', '1', 'success', NULL, 1, 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUES('device_command', 'Device instructions', 'Equipment service functions', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Remote locking', 'LOCK', NULL, NULL, 0, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Remote unlock', 'UNLOCK', NULL, NULL, 1, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Log in', 'SIGN_ON', NULL, NULL, 2, NULL, 0, 1, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Sign out', 'SIGN_OFF', NULL, NULL, 3, NULL, 0, 1, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'OTA upgrade', 'OTA_UPGRADE', NULL, NULL, 4, NULL, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUES('device_protocol_type', 'Device Protocol Type', 'Device Protocol Type', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'MQTT', 'MQTT', NULL, NULL, 0, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'TCP', 'TCP', NULL, NULL, 1, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'UDP', 'UDP', NULL, NULL, 2, NULL, 0, 1, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'BLE', 'BLE', NULL, NULL, 3, NULL, 0, 1, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'CoAP', 'CoAP', NULL, NULL, 4, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'LwM2M', 'LwM2M', NULL, NULL, 4, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Modbus', 'Modbus', NULL, NULL, 4, NULL, 0, 0, 10000, now(), 10000, now());


INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUES('device_property', 'Device Attributes', 'General device attributes: Operating status | App version | Battery percentage | Temperature', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Running status', 'RUNNING_STATUS', NULL, NULL, 0, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'APP version', 'APP_VERSION', NULL, NULL, 1, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Battery percentage', 'BATTERY_PERCENT', NULL, NULL, 2, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'temperature', 'TEMPERATURE', NULL, NULL, 3, NULL, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (dict_type,dict_name,remark,sort,tenant_id,version,deleted,creator,create_time,updater,update_time )VALUES('device_event_type', 'Event Type', 'Event Log Types', 0, 10000, 0, 0, 10000, now(), 10000, now() );
SET @typeId = @@identity;
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Offline', 'OFFLINE', 'danger', NULL, 1, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Online', 'ONLINE', 'primary', NULL, 2, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Log in', 'SIGN_ON', 'primary', NULL, 3, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Log out', 'SIGN_OFF', 'danger', NULL, 4, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'OTA upgrade', 'OTA_UPGRADE', 'primary', NULL, 5, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Remote device locking', 'LOCK', 'primary', NULL, 6, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Remote device unlocking', 'UNLOCK', 'primary', NULL,7, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'APP version information', 'APP_VERSION_REPORT', 'primary', NULL, 8, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Battery power', 'BATTERY_PERCENT_REPORT', 'primary', NULL, 9, NULL, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data  (dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ((SELECT @typeId), 'Temperature', 'TEMPERATURE_REPORT', 'primary', NULL, 0, NULL, 10, 0, 10000, now(), 10000, now());
