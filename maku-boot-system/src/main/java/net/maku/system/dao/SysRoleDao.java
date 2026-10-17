package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Role management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    /**
     * Get the maximum data scope of the user by user ID
     */
    Integer getDataScopeByUserId(@Param("userId") Long userId);

    /**
     * Get the user role codes by user ID
     */
    List<String> geRoleCodeByUserId(@Param("userId") Long userId);

}
