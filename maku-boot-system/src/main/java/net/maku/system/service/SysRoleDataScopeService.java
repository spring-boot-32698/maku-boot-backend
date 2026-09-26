package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysRoleDataScopeEntity;

import java.util.List;

/**
 * Role data permission
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysRoleDataScopeService extends BaseService<SysRoleDataScopeEntity> {

    /**
     * Save or update
     * @param roleId      Role ID
     * @param orgIdList   Organization ID list
     */
    void saveOrUpdate(Long roleId, List<Long> orgIdList);

    /**
     * Get the organization ID list by role ID
     */
    List<Long> getOrgIdList(Long roleId);

    /**
     * Delete role data-permission relationships by role id list
     * @param roleIdList Role id list
     */
    void deleteByRoleIdList(List<Long> roleIdList);
}