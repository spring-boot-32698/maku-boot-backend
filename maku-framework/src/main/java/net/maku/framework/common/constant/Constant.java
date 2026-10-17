package net.maku.framework.common.constant;

/**
 * Constant
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface Constant {
    /**
     * Root node identifier
     */
    Long ROOT = 0L;
    /**
     * Current page number
     */
    String PAGE = "page";
    /**
     * Data permission
     */
    String DATA_SCOPE = "dataScope";
    /**
     * Super administrator
     */
    Integer SUPER_ADMIN = 1;
    /**
     * Disabled
     */
    Integer DISABLE = 0;
    /**
     * Enabled
     */
    Integer ENABLE = 1;
    /**
     * Failed
     */
    Integer FAIL = 0;
    /**
     * Success
     */
    Integer SUCCESS = 1;
    /**
     * OK
     */
    String OK = "OK";

    /**
     * pgsqldriver of
     */
    String PGSQL_DRIVER = "org.postgresql.Driver";

    // Field name: primary key
    String COLUMN_ID = "id";
    // Field name: creator
    String COLUMN_CREATOR = "creator";
    // Field name: creation time
    String COLUMN_CREATE_TIME = "create_time";
    // Field name: updater
    String COLUMN_UPDATER = "updater";
    // Field name: update time
    String COLUMN_UPDATE_TIME = "update_time";
    // Field name: tenant ID
    String COLUMN_TENANT_ID = "tenant_id";

}