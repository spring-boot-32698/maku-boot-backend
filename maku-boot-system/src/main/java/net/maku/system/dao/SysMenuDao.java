package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Menu management
 * 
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysMenuEntity> {

	/**
	 * Query all menu list
	 *
	 * @param type Menu type
	 */
	List<SysMenuEntity> getMenuList(@Param("type") Integer type);

	/**
	 * Query user menu list
	 *
	 * @param userId User ID
	 * @param type Menu type
	 */
	List<SysMenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

	/**
	 * Query user permission list
	 * @param userId  User ID
	 */
	List<String> getUserAuthorityList(@Param("userId") Long userId);

	/**
	 * Query all permission list
	 */
	List<String> getAuthorityList();

}
