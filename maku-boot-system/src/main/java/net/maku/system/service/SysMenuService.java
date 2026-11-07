package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.entity.SysMenuEntity;
import net.maku.system.vo.SysMenuVO;

import java.util.List;
import java.util.Set;


/**
 * Menu management
 * 
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

	void save(SysMenuVO vo);

	void update(SysMenuVO vo);

	void delete(Long id);

	/**
	 * Menu list
	 *
	 * @param type Menu type
	 */
	List<SysMenuVO> getMenuList(Integer type);

	/**
	 * User menu list
	 *
	 * @param user  User
	 * @param type Menu type
	 */
	List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * Get the number of child menus
	 * @param pid  Parent menu ID
	 */
	Long getSubMenuCount(Long pid);

	/**
	 * Get user permission list
	 */
	Set<String> getUserAuthority(UserDetail user);
}
