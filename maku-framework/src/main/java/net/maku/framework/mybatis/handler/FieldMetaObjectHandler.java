package net.maku.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * mybatis-plus Auto-fill fields
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";
    private final static String ORG_ID = "orgId";
    private final static String VERSION = "version";
    private final static String DELETED = "deleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime now = LocalDateTime.now();

        // User field auto-fill
        if (user != null) {
            // Creator
            setFieldValByName(CREATOR, user.getId(), metaObject);
            // Updater
            setFieldValByName(UPDATER, user.getId(), metaObject);
            // Creator organization
            setFieldValByName(ORG_ID, user.getOrgId(), metaObject);
        }

        // Creation time
        setFieldValByName(CREATE_TIME, now, metaObject);
        // Update time
        setFieldValByName(UPDATE_TIME, now, metaObject);
        // Version number
        setFieldValByName(VERSION, 0, metaObject);
        // Delete flag
        setFieldValByName(DELETED, 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // Updater
        setFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
        // Update time
        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
    }
}