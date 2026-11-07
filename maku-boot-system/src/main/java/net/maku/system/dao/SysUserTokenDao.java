package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User Token
 *
 * @author Amu babamu@126.com
 */
@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {

    /**
     * Query the access_token list of online users by role ID
     *
     * @param roleId Role ID
     * @param time   Current time
     * @return Return the access_token list
     */
    List<String> getOnlineAccessTokenListByRoleId(@Param("roleId") Long roleId, @Param("time") LocalDateTime time);

    /**
     * Query the access_token list of online users by user ID
     *
     * @param userId User ID
     * @param time   Current time
     * @return Return the access_token list
     */
    List<String> getOnlineAccessTokenListByUserId(@Param("userId") Long userId, @Param("time") LocalDateTime time);

}