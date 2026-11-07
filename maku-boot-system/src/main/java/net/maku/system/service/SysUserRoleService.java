package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysUserRoleEntity;

import java.util.List;

/**
 * User-role relationship
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysUserRoleService extends BaseService<SysUserRoleEntity> {

    /**
     * Save or update
     * @param userId      User ID
     * @param roleIdList  Role ID list
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * Assign roles to user list
     * @param roleId      Role ID
     * @param userIdList  User ID list
     */
    void saveUserList(Long roleId, List<Long> userIdList);

    /**
     * Delete user-role relationships by role id list
     * @param roleIdList Role id
     */
    void deleteByRoleIdList(List<Long> roleIdList);

    /**
     * Delete user-role relationships by user id list
     * @param userIdList User id list
     */
    void deleteByUserIdList(List<Long> userIdList);

    /**
     * Delete user-role relationships by role id and user id list
     * @param roleId Role id
     * @param userIdList User id list
     */
    void deleteByUserIdList(Long roleId, List<Long> userIdList);

    /**
     * Role ID list
     * @param userId  User ID
     */
    List<Long> getRoleIdList(Long userId);

    /**
     * Query the user ID list by role ID
     * @param roleId  Role ID
     */
    List<Long> getExistsUserIdList(Long roleId);
}