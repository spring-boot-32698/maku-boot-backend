CREATE TABLE member_user
(
    id               bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
    nick_name        varchar(100) NOT NULL COMMENT 'Nick name',
    mobile           varchar(20) NOT NULL COMMENT 'Phone number',
    avatar           varchar(200) COMMENT 'avatar',
    birthday         date COMMENT 'Date of birth',
    gender           tinyint COMMENT 'Gender: 0: Male; 1: Female; 2: Unknown',
    openid           varchar(200) COMMENT 'Third-party platform, unique identifier',
    last_login_ip    varchar(100) COMMENT 'Last login IP',
    last_login_time  datetime COMMENT 'Last login time',
    tenant_id        bigint COMMENT 'Tenant ID',
    remark           varchar(500) COMMENT 'Remark',
    status           tinyint COMMENT 'Status 0: Disabled 1: Enabled',
    version          int COMMENT 'Version number',
    deleted          tinyint COMMENT 'Deletion indicator: 0: Normal; 1: Deleted',
    create_time      datetime COMMENT 'Creation time',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='Membership Management';