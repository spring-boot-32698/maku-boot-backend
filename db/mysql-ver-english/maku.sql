CREATE TABLE sys_user
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    username    varchar(50) NOT NULL COMMENT 'username',
    password    varchar(100) COMMENT 'password',
    real_name   varchar(50) COMMENT 'real name',
    avatar      varchar(200) COMMENT 'avatar',
    signature   varchar(200) COMMENT 'sign',
    gender      tinyint COMMENT 'gender   0：male   1：female   2：unknown',
    email       varchar(100) COMMENT 'email',
    mobile      varchar(20) COMMENT 'phone number',
    org_id      bigint COMMENT 'Organization ID',
    leader_id   bigint COMMENT 'Direct Supervisor ID',
    super_admin tinyint COMMENT 'Super Administrator   0：no   1：yes',
    status      tinyint COMMENT 'status  0：discontinued   1：normal',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Delete mark  0：normal   1：deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='User Management';

CREATE TABLE sys_user_token
(
    id                    bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    user_id               bigint COMMENT 'User ID',
    access_token          varchar(32) COMMENT 'accessToken',
    access_token_expire   datetime COMMENT 'accessToken expiration time',
    refresh_token         varchar(32) COMMENT 'refreshToken',
    refresh_token_expire  datetime COMMENT 'refreshToken expiration time',
    tenant_id             bigint COMMENT 'Tenant ID',
    create_time           datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='User Token';

CREATE TABLE sys_third_login
(
    id                    bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    open_type             varchar(50) COMMENT 'Open platform types',
    open_id               varchar(100) COMMENT 'Open platform, unique identifier',
    username              varchar(100) COMMENT 'username',
    user_id               bigint COMMENT 'User ID',
    tenant_id             bigint COMMENT 'Tenant ID',
    version               int COMMENT 'Version number',
    deleted               tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    create_time           datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Third-party login';

CREATE TABLE sys_third_login_config
(
    id                    bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    open_type             varchar(50) COMMENT 'Open platform types',
    client_id             varchar(200) COMMENT 'ClientID',
    client_secret         varchar(200) COMMENT 'ClientSecret',
    redirect_uri          varchar(200) COMMENT 'RedirectUri',
    agent_id              varchar(200) COMMENT 'AgentID',
    tenant_id             bigint COMMENT 'Tenant ID',
    version               int COMMENT 'Version number',
    deleted               tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    create_time           datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Third-party login configuration';

CREATE TABLE sys_org
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    pid         bigint COMMENT 'Superior ID',
    name        varchar(50) COMMENT 'Organization Name',
    sort        int COMMENT 'Sort',
    leader_id   bigint COMMENT 'Person in charge ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_sys_org_pid (pid)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Institutional Management';

create table sys_role
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    name        varchar(50) COMMENT 'Character Name',
    role_code   varchar(50) COMMENT 'Role coding',
    remark      varchar(100) COMMENT 'Remark',
    data_scope  tinyint COMMENT 'Data range: 0: All data; 1: Data of this organization and its subsidiaries; 2: Data of this organization; 3: Personal data; 4: Custom data.',
    org_id      bigint COMMENT 'Organization ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_sys_role_orgid (org_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Role Management';

create table sys_user_role
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_id     bigint COMMENT 'Character ID',
    user_id     bigint COMMENT 'User ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_sys_user_roleid (role_id),
    key idx_sys_user_userid (user_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='User Role Relationships';

CREATE TABLE sys_post
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    post_code   varchar(100) COMMENT 'Job code',
    post_name   varchar(100) COMMENT 'Job Title',
    sort        int COMMENT 'Sort',
    status      tinyint COMMENT 'Status 0: Disabled 1: Normal',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Job Management';

CREATE TABLE sys_user_post
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    user_id     bigint COMMENT 'User ID',
    post_id     bigint COMMENT 'Job ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_user_post_id (user_id),
    key idx_user_post_postid (post_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='User job relationship';

create table sys_menu
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    pid         bigint COMMENT 'Superior ID',
    name        varchar(200) COMMENT 'Menu Name',
    url         varchar(200) COMMENT 'Menu URL',
    authority   varchar(500) COMMENT 'Authorization identifiers (multiple identifiers separated by commas, e.g., sys:menu:list, sys:menu:save)',
    type        tinyint COMMENT 'Type 0: Menu; 1: Button; 2: Interface',
    open_style  tinyint COMMENT 'Opening method: 0: Internal 1: External',
    icon        varchar(50) COMMENT 'Menu icon',
    sort        int COMMENT 'Sort',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_menu_pid (pid)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Menu Management';

create table sys_role_menu
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_id     bigint COMMENT 'Character ID',
    menu_id     bigint COMMENT 'Menu ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_roleid (role_id),
    key idx_role_menuid (menu_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Character Menu Relationship';

create table sys_role_data_scope
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    role_id     bigint COMMENT 'Character ID',
    org_id      bigint COMMENT 'Organization ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id),
    key idx_data_scope_roleid (role_id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Character data permissions';

create table sys_dict_type
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    dict_type   varchar(100) NOT NULL COMMENT 'dictionary type',
    dict_name   varchar(255) NOT NULL COMMENT 'Dictionary name',
    dict_source tinyint default 0 COMMENT 'Source 0: Dictionary data; Source 1: Dynamic SQL',
    dict_sql    varchar(500) COMMENT 'Dynamic SQL',
    remark      varchar(255) COMMENT 'Remark',
    sort        int COMMENT 'Sort',
    pid         bigint COMMENT 'upper node',
    has_child   tinyint default 0 COMMENT 'Does it have child nodes',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='dictionary type';

create table sys_dict_data
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    dict_type_id bigint NOT NULL COMMENT 'Dictionary type ID',
    dict_label   varchar(255) NOT NULL COMMENT 'Dictionary tags',
    dict_value   varchar(255) COMMENT 'dictionary value',
    label_class  varchar(100) COMMENT 'Tag Style',
    remark       varchar(255) COMMENT 'Remark',
    sort         int COMMENT 'Sort',
    tenant_id    bigint COMMENT 'Tenant ID',
    version      int COMMENT 'Version number',
    deleted      tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator      bigint COMMENT 'Creator',
    create_time  datetime COMMENT 'Creation time',
    updater      bigint COMMENT 'Updater',
    update_time  datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Dictionary data';

create table sys_attachment
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    name         varchar(255) NOT NULL COMMENT 'Attachment Name',
    url          varchar(255) NOT NULL COMMENT 'Attachment address',
    size_        bigint COMMENT 'Attachment size',
    platform     varchar(50) COMMENT 'Storage Platform',
    tenant_id    bigint COMMENT 'Tenant ID',
    version      int COMMENT 'Version number',
    deleted      tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator      bigint COMMENT 'Creator',
    create_time  datetime COMMENT 'Creation time',
    updater      bigint COMMENT 'Updater',
    update_time  datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Attachment Management';

create table sys_params
(
    id            bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    param_name    varchar(100) COMMENT 'Parameter name',
    param_type    tinyint NOT NULL COMMENT 'System parameter 0: No 1: Yes',
    param_key     varchar(100) COMMENT 'Parameter key',
    param_value   varchar(2000) COMMENT 'Parameter value',
    remark        varchar(200) COMMENT 'Remark',
    tenant_id     bigint COMMENT 'Tenant ID',
    version       int COMMENT 'Version number',
    deleted       tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator       bigint COMMENT 'Creator',
    create_time   datetime COMMENT 'Creation time',
    updater       bigint COMMENT 'Updater',
    update_time   datetime COMMENT 'Update time',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT='Parameter Management';

create table sys_log_login
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    username     varchar(50) COMMENT 'username',
    ip           varchar(32) COMMENT 'Login IP',
    address      varchar(32) COMMENT 'Login location',
    user_agent   varchar(500) COMMENT 'User Agent',
    status       tinyint COMMENT 'Login status: 0: Failure; 1: Success',
    operation    tinyint unsigned COMMENT 'Operation information: 0: Login successful; 1: Logout successful; 2: Incorrect verification code; 3: Incorrect username or password.',
    tenant_id    bigint COMMENT 'Tenant ID',
    create_time  datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Login Log';

create table sys_log_operate
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    module       varchar(100) COMMENT 'Module Name',
    name         varchar(100) COMMENT 'Operation name',
    req_uri      varchar(200) DEFAULT NULL COMMENT 'Request URI',
    req_method   varchar(20) DEFAULT NULL COMMENT 'Request method',
    req_params   text COMMENT 'Request parameters',
    ip           varchar(32) COMMENT 'Operation IP',
    address      varchar(32) COMMENT 'Login location',
    user_agent   varchar(500) COMMENT 'User Agent',
    operate_type tinyint COMMENT 'Operation type',
    duration     int NOT NULL COMMENT 'Execution time',
    status       tinyint COMMENT 'Operation status: 0: Failure; 1: Success',
    user_id      bigint COMMENT 'User ID',
    real_name    varchar(50) COMMENT 'Operator',
    result_msg   varchar(500) COMMENT 'Return message',
    tenant_id    bigint COMMENT 'Tenant ID',
    create_time  datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COMMENT ='Operation Log';


CREATE TABLE sys_sms_config
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform    tinyint COMMENT 'Platform Type: 0: Alibaba Cloud; 1: Tencent Cloud; 2: Qiniu Cloud; 3: Huawei Cloud',
    group_name  varchar(100)  COMMENT 'Group name: You can specify a group when sending an SMS.',
    sign_name   varchar(100)  COMMENT 'SMS signature',
    template_id varchar(100) COMMENT 'SMS templates',
    app_id      varchar(100) COMMENT 'SMS application ID, such as Tencent Cloud, etc.',
    sender_id   varchar(100) COMMENT 'Tencent Cloud international SMS, Huawei Cloud, etc. require',
    url         varchar(200) COMMENT 'Access address, such as: Huawei Cloud',
    access_key  varchar(100) COMMENT 'AccessKey',
    secret_key  varchar(100) COMMENT 'SecretKey',
    status      tinyint COMMENT 'Status 0: Disabled 1: Enabled',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='SMS Configuration';

CREATE TABLE sys_sms_log
(
    id             bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform_id    bigint COMMENT 'Platform ID',
    platform       tinyint COMMENT 'Platform type',
    mobile         varchar(20) NOT NULL COMMENT 'Phone number',
    params         varchar(200) COMMENT 'parameter',
    status         tinyint COMMENT 'Status 0: Failure 1: Success',
    error          varchar(2000) COMMENT 'Abnormal information',
    create_time    datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='SMS Log';


CREATE TABLE sys_mail_config
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform    tinyint COMMENT 'Platform type -1: Local 0: Alibaba Cloud',
    group_name  varchar(100) COMMENT 'Group name: You can specify a group when sending an email.',
    mail_host   varchar(100) COMMENT 'SMTP server',
    mail_port   int COMMENT 'SMTP port',
    mail_from   varchar(100) COMMENT 'sender email address',
    mail_pass   varchar(100) COMMENT 'sender password',
    region_id   varchar(100) COMMENT 'regionId',
    endpoint    varchar(100) COMMENT 'Alibaba Cloud endpoint',
    access_key  varchar(100) COMMENT 'AccessKey',
    secret_key  varchar(100) COMMENT 'SecretKey',
    status      tinyint COMMENT 'Status 0: Disabled 1: Enabled',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Email platform';

CREATE TABLE sys_mail_log
(
    id             bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform_id    bigint COMMENT 'Platform ID',
    platform       tinyint COMMENT 'Platform type',
    mail_from      varchar(100) COMMENT 'sender email address',
    mail_tos       varchar(1000) COMMENT 'Recipient email address',
    subject        varchar(200) COMMENT 'Email Subject',
    content        text COMMENT 'Email content',
    status         tinyint COMMENT 'Status 0: Failure 1: Success',
    error          varchar(2000) COMMENT 'Abnormal information',
    create_time    datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Email logs';

INSERT INTO sys_user (id, username, password, real_name, avatar, gender, email, mobile, status, org_id, super_admin, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (10000, 'admin', 'dc1fd00e3eeeb940ff46f457bf97d66ba7fcc36e0b20802383de142860e76ae6', 'admin', 'https://cdn.maku.net/images/avatar.png', 0, 'babamu@126.com', '13612345678', 1, null, 1, 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (1, NULL, 'System Settings', NULL, NULL, 0, 0, 'icon-setting', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (2, 1, 'Menu Management', 'sys/menu/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (3, 2, 'Check', '', 'sys:menu:list', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (4, 2, 'New', '', 'sys:menu:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (5, 2, 'Modify', '', 'sys:menu:update,sys:menu:info', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (6, 2, 'Delete', '', 'sys:menu:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (7, 1, 'Data dictionary', 'sys/dict/type', '', 0, 0, 'icon-insertrowabove', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (8, 7, 'Query', '', 'sys:dict:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (9, 7, 'Save', '', 'sys:dict:save', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (10, 7, 'Update', '', 'sys:dict:update,sys:dict:info', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (11, 7, 'Delete', '', 'sys:dict:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (12, NULL, 'Access Management', '', '', 0, 0, 'icon-safetycertificate', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (13, 12, 'Job Management', 'sys/post/index', '', 0, 0, 'icon-solution', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (14, 13, 'Query', '', 'sys:post:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (15, 13, 'New', '', 'sys:post:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (16, 13, 'Revise', '', 'sys:post:update,sys:post:info', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (17, 13, 'Delete', '', 'sys:post:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (18, 12, 'Institutional Management', 'sys/org/index', '', 0, 0, 'icon-cluster', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (19, 18, 'Query', '', 'sys:org:list', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (20, 18, 'New', '', 'sys:org:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (21, 18, 'Revise', '', 'sys:org:update,sys:org:info', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (22, 18, 'Delete', '', 'sys:org:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (23, 12, 'Role Management', 'sys/role/index', '', 0, 0, 'icon-team', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (24, 23, 'Query', '', 'sys:role:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (25, 23, 'New', '', 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (26, 23, 'Revise', '', 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (27, 23, 'Delete', '', 'sys:role:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (28, 12, 'User Management', 'sys/user/index', '', 0, 0, 'icon-user', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (29, 28, 'Query', '', 'sys:user:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (30, 28, 'New', '', 'sys:user:save,sys:role:list', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (31, 28, 'Revise', '', 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (32, 28, 'Delete', '', 'sys:user:delete', 1, 0, '', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (33, NULL, 'Application Management', '', '', 0, 0, 'icon-appstore', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (34, NULL, 'Log Management', '', '', 0, 0, 'icon-filedone', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (35, 34, 'Login Log', 'sys/log/login', 'sys:log:login', 0, 0, 'icon-solution', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (36, 28, 'Import', '', 'sys:user:import', 1, 0, '', 5, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (37, 28, 'Export', '', 'sys:user:export', 1, 0, '', 6, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (38, 1, 'Parameter Management', 'sys/params/index', 'sys:params:all', 0, 0, 'icon-filedone', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (39, 1, 'API documentation', '{{apiUrl}}/doc.html', null, 0, 1, 'icon-file-text-fill', 10, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (40, 34, 'Operation Log', 'sys/log/operate', 'sys:operate:all', 0, 0, 'icon-file-text', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (41, 1, 'System Configuration', 'sys/config/index', null, 0, 0, 'icon-safetycertificate', 4, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (42, 41, 'SMS Configuration', '', 'sys:sms:config', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (43, 41, 'Email Configuration', '', 'sys:mail:config', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (44, 41, 'Third-party login', '', 'sys:third:config', 1, 0, '', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (45, NULL, 'Basic tools', '', '', 0, 0, 'icon-wrench-fill', 5, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (46, 45, 'SMS sending', 'sys/tool/sms/index', 'sys:sms:log', 0, 0, 'icon-message', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (47, 45, 'Email sending', 'sys/tool/mail/index', 'sys:mail:log', 0, 0, 'icon-mail', 2, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (48, 45, 'Attachment Management', 'sys/attachment/index', NULL, 0, 0, 'icon-folder-fill', 3, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (49, 48, 'Check', '', 'sys:attachment:page', 1, 0, '', 0, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (50, 48, 'Upload', '', 'sys:attachment:save', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (51, 48, 'Delete', '', 'sys:attachment:delete', 1, 0, '', 1, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_menu (id, pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (52, NULL, 'Commercial Licensing', 'https://maku.net/price', NULL, 0, 1, 'icon-safetycertificate', 10, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (1, 'post_status', 'state', 'Job Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (2, 'user_gender', 'gender', 'User Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (3, 'user_status', 'state', 'User Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (4, 'role_data_scope', 'Data range', 'Role Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (5, 'enable_disable', 'state', 'Function status: Enabled | Disabled', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (6, 'success_fail', 'state', 'Operation status: Success | Failure', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (7, 'login_operation', 'Operation information', 'Login Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (8, 'params_type', 'System parameters', 'Parameter Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (9, 'user_super_admin', 'Is the user a super admin','Is the user a super admin', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (10, 'log_operate_type', 'Operation type', 'Operation Log', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (11, 'sms_platform', 'SMS platform types', 'SMS Management', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_type (id, dict_type, dict_name, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (12, 'mail_platform', 'Email platform types', 'Email Management', 0, 10000, 0, 0, 10000, now(), 10000, now());


INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (1, 1, 'Discontinued', '0', 'danger', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (2, 1, 'Normal', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (3, 2, 'male', '0', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (4, 2, 'female', '1', 'success', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (5, 2, 'unknown', '2', 'warning', '', 2, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (6, 3, 'normal', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (7, 3, 'Discontinued', '0', 'danger', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (8, 4, 'All data', '0', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (9, 4, 'Data of this organization and its subsidiaries', '1', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (10, 4, 'Data from this organization', '2', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (11, 4, 'My data', '3', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (12, 4, 'Custom data', '4', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (13, 5, 'Disable', '0', 'danger', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (14, 5, 'Enable', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (15, 6, 'fail', '0', 'danger', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (16, 6, 'success', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (17, 7, 'Login successful', '0', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (18, 7, 'Exit successful', '1', 'warning', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (19, 7, 'Verification code error', '2', 'danger', '', 2, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (20, 7, 'Incorrect account password', '3', 'danger', '', 3, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (21, 8, 'no', '0', 'primary', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (22, 8, 'yes', '1', 'danger', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (23, 9, 'yes', '1', 'danger', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (24, 9, 'no', '0', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (25, 10, 'other', '0', 'info', '', 10, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (26, 10, 'Query', '1', 'primary', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (27, 10, 'New', '2', 'success', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (28, 10, 'Revise', '3', 'warning', '', 2, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (29, 10, 'Delete', '4', 'danger', '', 3, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (30, 10, 'Export', '5', 'info', '', 4, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (31, 10, 'Import', '6', 'info', '', 5, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (32, 11, 'Alibaba Cloud', '0', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (33, 11, 'Tencent Cloud', '1', '', '', 1, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (34, 11, 'Qiniu Cloud', '2', '', '', 2, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (35, 11, 'Huawei Cloud', '3', '', '', 3, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (36, 12, 'Local', '-1', '', '', 0, 10000, 0, 0, 10000, now(), 10000, now());
INSERT INTO sys_dict_data (id, dict_type_id, dict_label, dict_value, label_class, remark, sort, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES (37, 12, 'Alibaba Cloud', '0', '', '', 1, 10000, 0, 0, 10000, now(), 10000, now());


INSERT INTO sys_params (param_name, param_type, param_key, param_value, remark, tenant_id, version, deleted, creator, create_time, updater, update_time) VALUES ('User Login - Verification Code Switch', 1, 'LOGIN_CAPTCHA', 'false', 'Enable CAPTCHA (true: enable, false: disable)', 10000, 0, 0, 10000, now(), 10000, now());

INSERT INTO sys_third_login_config (open_type, client_id, client_secret, redirect_uri, agent_id, tenant_id, version, deleted, create_time) VALUES ('feishu', 'cli_a541d3aa03f8500b', '5Chz39zvEhZtxSVZz3vLjfQHdkvavQaH', 'http://localhost:8080/sys/third/callback/feishu', '', 10000, 0, 0, now());

INSERT INTO sys_mail_config (platform, group_name, mail_host, mail_port, mail_from, mail_pass, region_id, endpoint, access_key, secret_key, status, version, deleted, creator, create_time, updater, update_time) VALUES (-1, 'test', NULL, NULL, 'baba_tv@163.com', 'TZNVURLYVBNJUNBB', '', '', NULL, NULL, 1, 1, 0, 10000, now(), 10000, now());