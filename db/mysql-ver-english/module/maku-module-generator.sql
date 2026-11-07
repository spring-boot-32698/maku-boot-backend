INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (33, 'code generator', '{{apiUrl}}/maku-generator/index.html', '', 0, 0, 'icon-rocket', 2, 0, 0, 10000, now(), 10000, now());

CREATE TABLE gen_datasource
(
    id          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    db_type     varchar(200) COMMENT 'Database type',
    conn_name   varchar(200) NOT NULL COMMENT 'Connection name',
    conn_url    varchar(500) COMMENT 'URL',
    username    varchar(200) COMMENT 'username',
    password    varchar(200) COMMENT 'password',
    create_time datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Data source management';

CREATE TABLE gen_config
(
    id             bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    config_key     varchar(200),
    config_value   varchar(2000),
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Configuration information';

CREATE TABLE gen_field_type
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    column_type  varchar(200) COMMENT 'Field type',
    attr_type    varchar(200) COMMENT 'Attribute type',
    package_name varchar(200) COMMENT 'Property Package Name',
    create_time  datetime COMMENT 'Creation time',
    primary key (id),
    unique key idx_gen_columntype (column_type)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Field type management';

CREATE TABLE gen_base_class
(
    id           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    package_name varchar(200) COMMENT 'base class package name',
    code         varchar(200) COMMENT 'Base class encoding',
    fields       varchar(500) COMMENT 'Base class fields, multiple fields separated by commas.',
    remark       varchar(200) COMMENT 'Remark',
    create_time  datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Base class management';

CREATE TABLE gen_table
(
    id             bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    table_name     varchar(200) COMMENT 'Table name',
    class_name     varchar(200) COMMENT 'Class Name',
    table_comment  varchar(200) COMMENT 'illustrate',
    author         varchar(200) COMMENT 'author',
    email          varchar(200) COMMENT 'Mail',
    package_name   varchar(200) COMMENT 'Project package name',
    version        varchar(200) COMMENT 'Project version number',
    generator_type tinyint COMMENT 'Generation method 0: zip archive 1: custom directory',
    backend_path   varchar(500) COMMENT 'Backend generated path',
    frontend_path  varchar(500) COMMENT 'Front-end generation path',
    module_name    varchar(200) COMMENT 'Module Name',
    function_name  varchar(200) COMMENT 'Function name',
    form_layout    tinyint COMMENT 'Form layout 1: One column 2: Two columns',
    table_type       tinyint COMMENT 'Table type',
    sub_table        varchar(4000) COMMENT 'Sub-table data',
    table_operation  varchar(200) COMMENT 'Generation function',
    auth_level       tinyint COMMENT 'Permission level 0: Page level; 1: Button level',
    open_type        tinyint COMMENT 'Add/Edit: 0: Pop-up window; 1: Right sidebar',
    request_url      varchar(200) COMMENT 'Request URL',
    authority        varchar(200) COMMENT 'Permission ID',
    tree_id          varchar(200) COMMENT 'Tree ID',
    tree_pid         varchar(200) COMMENT 'Tree parent ID',
    tree_label       varchar(200) COMMENT 'Tree display column',
    left_title       varchar(200) COMMENT 'Left title',
    left_from        tinyint COMMENT 'Data source',
    left_table_name     varchar(200) COMMENT 'Left side tree table name',
    left_url            varchar(200) COMMENT 'Interface address',
    left_relation_field varchar(200) COMMENT 'Related fields',
    datasource_id  bigint COMMENT 'Data source ID',
    baseclass_id   bigint COMMENT 'Base class ID',
    create_time    datetime COMMENT 'Creation time',
    primary key (id),
    unique key idx_gen_tablename (table_name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Code generation table';

CREATE TABLE gen_table_field
(
    id              bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    table_id        bigint COMMENT 'Table ID',
    field_name      varchar(200) COMMENT 'Field Name',
    field_type      varchar(200) COMMENT 'Field type',
    field_comment   varchar(200) COMMENT 'Field Description',
    attr_name       varchar(200) COMMENT 'Attribute Name',
    attr_type       varchar(200) COMMENT 'Attribute type',
    package_name    varchar(200) COMMENT 'Property Package Name',
    sort            int COMMENT 'Sort',
    auto_fill       varchar(20) COMMENT 'Automatically fill DEFAULT, INSERT, UPDATE, and INSERT_UPDATE statements.',
    primary_pk      tinyint COMMENT 'Primary key 0: No 1: Yes',
    base_field      tinyint COMMENT 'Base class field: 0: No; 1: Yes',
    form_item       tinyint COMMENT 'Form field 0: No 1: Yes',
    form_required   tinyint COMMENT 'Required field: 0: No 1: Yes',
    form_type       varchar(200) COMMENT 'Form types',
    form_dict       varchar(200) COMMENT 'Form dictionary type',
    form_validator  varchar(200) COMMENT 'Form validation',
    grid_item       tinyint COMMENT 'List item 0: No 1: Yes',
    grid_sort       tinyint COMMENT 'List sorting: 0: No; 1: Yes',
    query_item      tinyint COMMENT 'Query item 0: No 1: Yes',
    query_type      varchar(200) COMMENT 'Search methods',
    query_form_type varchar(200) COMMENT 'Query form type',
    create_time     datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Code generates table fields';

CREATE TABLE gen_project_modify
(
    id                     bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    project_name           varchar(100) COMMENT 'Project Name',
    project_code           varchar(100) COMMENT 'Project Identifier',
    project_package        varchar(100) COMMENT 'Project package name',
    project_path           varchar(200) COMMENT 'Project path',
    modify_project_name    varchar(100) COMMENT 'Change project name',
    modify_project_code    varchar(100) COMMENT 'Change label',
    modify_project_package varchar(100) COMMENT 'Change package name',
    exclusions             varchar(200) COMMENT 'Excluded files',
    modify_suffix          varchar(200) COMMENT 'Change documents',
    modify_tmp_path        varchar(100) COMMENT 'Change temporary path',
    create_time            datetime COMMENT 'Creation time',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Project name change';


-- Table structure used for testing the code generator --
CREATE TABLE gen_test_member
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        varchar(50) COMMENT 'Name',
    gender      tinyint COMMENT 'gender',
    age         int COMMENT 'age',
    tenant_id   bigint COMMENT 'Tenant ID',
    create_time datetime COMMENT 'Creation time',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Single table test';

CREATE TABLE gen_test_tree
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    parent_id   bigint COMMENT 'Superior ID',
    tree_name   varchar(100) COMMENT 'name',
    tenant_id   bigint COMMENT 'Tenant ID',
    create_time datetime COMMENT 'Creation time',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Tree table test';

CREATE TABLE gen_test_product
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        varchar(100) COMMENT 'name',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Delete mark',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Product Testing';

CREATE TABLE gen_test_product_info
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    images      varchar(2000) COMMENT 'picture',
    intro       varchar(4000) COMMENT 'introduce',
    product_id  bigint COMMENT 'Product ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Delete mark',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Product Information';

CREATE TABLE gen_test_product_param
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    param_name  varchar(200) COMMENT 'Parameter name',
    param_value varchar(200) COMMENT 'Parameter value',
    product_id  bigint COMMENT 'Product ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    version     int COMMENT 'Version number',
    deleted     tinyint COMMENT 'Delete mark',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Product Parameters';

CREATE TABLE gen_test_goods_category
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        varchar(100) COMMENT 'name',
    pid         bigint COMMENT 'Superior ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    deleted     tinyint COMMENT 'Delete mark',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Product Categories';

CREATE TABLE gen_test_goods
(
    id          bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        varchar(100) COMMENT 'name',
    intro       varchar(4000) COMMENT 'introduce',
    category_id bigint COMMENT 'Category ID',
    tenant_id   bigint COMMENT 'Tenant ID',
    deleted     tinyint COMMENT 'Delete mark',
    creator     bigint COMMENT 'Creator',
    create_time datetime COMMENT 'Creation time',
    updater     bigint COMMENT 'Updater',
    update_time datetime COMMENT 'Update time',
    PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Product Management';


INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('datetime', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('date', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinyint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('smallint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('integer', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bigint', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('float', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('double', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('decimal', 'BigDecimal', 'java.math.BigDecimal', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bit', 'Boolean', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('char', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('varchar', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinytext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('text', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('longtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('timestamp', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NUMBER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_INTEGER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_FLOAT', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_DOUBLE', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('VARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('CLOB', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int8', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int4', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int2', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('numeric', 'BigDecimal', 'java.math.BigDecimal', now());

INSERT INTO gen_base_class (package_name, code, fields, remark, create_time) VALUES ('net.maku.framework.mybatis.entity', 'BaseEntity', 'id,creator,create_time,updater,update_time,version,deleted', 'Use this base class，The table needs to have these fields.', now());

INSERT INTO gen_project_modify (project_name, project_code, project_package, project_path, modify_project_name, modify_project_code, modify_project_package, exclusions, modify_suffix, create_time) VALUES ('maku-boot', 'maku', 'net.maku', 'D:/makunet/maku-boot', 'baba-boot', 'baba', 'com.baba', '.git,.idea,target,logs', 'java,xml,yml,txt', now());
INSERT INTO gen_project_modify (project_name, project_code, project_package, project_path, modify_project_name, modify_project_code, modify_project_package, exclusions, modify_suffix, create_time) VALUES ('maku-cloud', 'maku', 'net.maku', 'D:/makunet/maku-cloud', 'baba-cloud', 'baba', 'com.baba', '.git,.idea,target,logs', 'java,xml,yml,txt', now());

INSERT INTO gen_config (config_key, config_value) VALUES ('gen_config', '');

INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (20, 'gen_test_member', 'GenTestMember', 'Single table test', 'Amu', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'member', 1, 0, NULL, '["query","insert","update","delete","export","import"]', 0, 0, '/test/member', 'test:member', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (21, 'gen_test_tree', 'GenTestTree', 'Tree table test', 'Amu', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'tree', 1, 1, NULL, '["query","insert","update","delete","export"]', 0, 0, '/test/tree', 'test:tree', 'id', 'parent_id', 'tree_name', 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (22, 'gen_test_goods', 'GenTestGoods', 'Product Management', 'Amu', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'goods', 1, 2, '[]', '["query","insert","update","delete"]', 0, 0, '/test/goods', 'test:goods', NULL, NULL, NULL, 0, NULL, now(), 0, 'gen_test_goods_category', '/test/category/list', 'category_id', 'Category list');
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (23, 'gen_test_goods_category', 'GenTestGoodsCategory', 'Product Categories', 'Amu', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'category', 1, 1, '[]', '["query","insert","update","delete"]', 0, 0, '/test/category', 'test:category', 'id', 'pid', 'name', 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (24, 'gen_test_product_info', 'GenTestProductInfo', 'Product Information', 'Amu', 'babamu@126.com', 'net.maku', '', 0, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'info', 1, 0, NULL, '["query","insert","update","delete","export"]', 0, 0, '/test/info', 'test:info', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (25, 'gen_test_product_param', 'GenTestProductParam', 'Product Parameters', 'Amu', 'babamu@126.com', 'net.maku', '', 0, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'param', 1, 0, NULL, '["query","insert","update","delete","export"]', 0, 0, '/test/param', 'test:param', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (26, 'gen_test_product', 'GenTestProduct', 'Product Testing', 'Amu', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'product', 1, 0, '[{"tableName":"gen_test_product_info","foreignKey":"product_id","tableTitle":"Product Information","mainRelation":1,"sort":0},{"tableName":"gen_test_product_param","foreignKey":"product_id","tableTitle":"Product Parameters","mainRelation":2,"sort":1}]', '["query","insert","update","delete"]', 0, 1, '/test/product', 'test:product', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);

INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (220, 20, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (221, 20, 'name', 'varchar', 'Name', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (222, 20, 'gender', 'tinyint', 'gender', 'gender', 'Integer', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'radio', 'user_gender', NULL, 1, 0, 1, '=', 'select', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (223, 20, 'age', 'int', 'age', 'age', 'Integer', NULL, 3, 'DEFAULT', 0, 0, 1, 1, 'number', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (224, 20, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (225, 20, 'version', 'int', 'Version number', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (226, 20, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (227, 20, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (228, 21, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (229, 21, 'tree_name', 'varchar', 'name', 'treeName', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (230, 21, 'parent_id', 'bigint', 'Superior ID', 'parentId', 'Long', NULL, 2, 'DEFAULT', 0, 0, 1, 0, 'treeselect', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (231, 21, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (232, 21, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'date', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (233, 22, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (234, 22, 'name', 'varchar', 'name', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (235, 22, 'intro', 'varchar', 'introduce', 'intro', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (236, 22, 'category_id', 'bigint', 'Category ID', 'categoryId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (237, 22, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (238, 22, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (239, 22, 'creator', 'bigint', 'Creator', 'creator', 'Long', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (240, 22, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (241, 22, 'updater', 'bigint', 'Updater', 'updater', 'Long', NULL, 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (242, 22, 'update_time', 'datetime', 'Update time', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (243, 23, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (244, 23, 'name', 'varchar', 'name', 'name', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (245, 23, 'pid', 'bigint', 'Superior ID', 'pid', 'Long', NULL, 1, 'DEFAULT', 0, 0, 1, 0, 'treeselect', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (246, 23, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (247, 23, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (248, 23, 'creator', 'bigint', 'Creator', 'creator', 'Long', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (249, 23, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'datetime', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (250, 23, 'updater', 'bigint', 'Updater', 'updater', 'Long', NULL, 7, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (251, 23, 'update_time', 'datetime', 'Update time', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (252, 24, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (253, 24, 'images', 'varchar', 'picture', 'images', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'image', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (254, 24, 'intro', 'varchar', 'introduce', 'intro', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'editor', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (255, 24, 'product_id', 'bigint', 'Product ID', 'productId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (256, 24, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (257, 24, 'version', 'int', 'Version number', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (258, 24, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (259, 24, 'creator', 'bigint', 'Creator', 'creator', 'Long', NULL, 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (260, 24, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (261, 24, 'updater', 'bigint', 'Updater', 'updater', 'Long', NULL, 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (262, 24, 'update_time', 'datetime', 'Update time', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 10, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (263, 25, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (264, 25, 'param_name', 'varchar', 'Parameter name', 'paramName', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (265, 25, 'param_value', 'varchar', 'Parameter value', 'paramValue', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (266, 25, 'product_id', 'bigint', 'Product ID', 'productId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (267, 25, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (268, 25, 'version', 'int', 'Version number', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (269, 25, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (270, 25, 'creator', 'bigint', 'Creator', 'creator', 'Long', NULL, 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (271, 25, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (272, 25, 'updater', 'bigint', 'Updater', 'updater', 'Long', NULL, 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (273, 25, 'update_time', 'datetime', 'Update time', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 10, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (274, 26, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (275, 26, 'name', 'varchar', 'name', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (276, 26, 'tenant_id', 'bigint', 'Tenant ID', 'tenantId', 'Long', NULL, 2, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (277, 26, 'version', 'int', 'Version number', 'version', 'Integer', NULL, 3, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (278, 26, 'deleted', 'tinyint', 'Delete mark', 'deleted', 'Integer', NULL, 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (279, 26, 'creator', 'bigint', 'Creator', 'creator', 'Long', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (280, 26, 'create_time', 'datetime', 'Creation time', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'datetime', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (281, 26, 'updater', 'bigint', 'Updater', 'updater', 'Long', NULL, 7, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (282, 26, 'update_time', 'datetime', 'Update time', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
