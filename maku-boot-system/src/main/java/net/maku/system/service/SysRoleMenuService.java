package net.maku.system.service;


import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * Role-menu mapping
 * 
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

	/**
	 * Get the menu ID list by role ID
	 */
	List<Long> getMenuIdList(Long roleId);

	/**
	 * Save or update
	 * @param roleId      Role ID
	 * @param menuIdList  Menu ID list
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * Delete role-menu relationships by role id list
	 * @param roleIdList Role id list
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * Delete role-menu relationships by menu id
	 * @param menuId Menu id
	 */
	void deleteByMenuId(Long menuId);
}