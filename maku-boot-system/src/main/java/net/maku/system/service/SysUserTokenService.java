package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysUserTokenEntity;
import net.maku.system.vo.SysUserTokenVO;


/**
 * User Token
 *
 * @author Amu babamu@126.com
 */
public interface SysUserTokenService extends BaseService<SysUserTokenEntity> {

    /**
     * Generate the user Token by user ID
     *
     * @param userId User ID
     * @return User Token
     */
    SysUserTokenVO createToken(Long userId);

    /**
     * Generate a new Token by refreshToken
     *
     * @param refreshToken refreshToken
     * @return User Token
     */
    SysUserTokenVO refreshToken(String refreshToken);

    /**
     * TokenExpired
     *
     * @param userId User ID
     */
    void expireToken(Long userId);

    /**
     * Update the user cached permissions by role ID
     *
     * @param roleId Role ID
     */
    void updateCacheAuthByRoleId(Long roleId);

    /**
     * Update the user cached permissions by user ID
     *
     * @param userId User ID
     */
    void updateCacheAuthByUserId(Long userId);
}