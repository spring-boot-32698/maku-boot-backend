package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysRoleDataScopeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Role data permission
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysRoleDataScopeDao extends BaseDao<SysRoleDataScopeEntity> {

    /**
     * Get the organization ID list by role ID
     */
    List<Long> getOrgIdList(@Param("roleId") Long roleId);

    /**
     * Get the user data permission list
     */
    List<Long> getDataScopeList(@Param("userId") Long userId);

}