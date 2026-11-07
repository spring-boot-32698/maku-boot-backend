package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User-role relationship
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * Role ID list
     * @param userId  User ID
     *
     * @return  Return the role ID list
     */
    List<Long> getRoleIdList(@Param("userId") Long userId);

    /**
     * Query the user ID list by role ID
     * @param roleId  Role ID
     *
     * @return  Return the user ID list
     */
    List<Long> getExistsUserIdList(@Param("roleId") Long roleId);
}