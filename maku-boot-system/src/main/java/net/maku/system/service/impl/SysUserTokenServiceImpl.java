package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import net.maku.framework.common.exception.ErrorCode;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.framework.security.cache.TokenStoreCache;
import net.maku.framework.security.properties.SecurityProperties;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.security.utils.TokenUtils;
import net.maku.system.dao.SysUserDao;
import net.maku.system.dao.SysUserTokenDao;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.entity.SysUserTokenEntity;
import net.maku.system.service.SysUserDetailsService;
import net.maku.system.service.SysUserTokenService;
import net.maku.system.vo.SysUserTokenVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * User Token
 *
 * @author Amu babamu@126.com
 */
@Service
@AllArgsConstructor
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    private final TokenStoreCache tokenStoreCache;
    private final SysUserDetailsService sysUserDetailsService;
    private final SecurityProperties securityProperties;
    private final SysUserDao sysUserDao;

    @Override
    public SysUserTokenVO createToken(Long userId) {
        // Generate token
        String accessToken = TokenUtils.generator();
        String refreshToken = TokenUtils.generator();

        SysUserTokenEntity entity = new SysUserTokenEntity();
        entity.setUserId(userId);
        entity.setAccessToken(accessToken);
        entity.setRefreshToken(refreshToken);

        // Expiration time
        Date now = new Date();
        entity.setAccessTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getAccessTokenExpire())));
        entity.setRefreshTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getRefreshTokenExpire())));

        // Whether a Token exists
        SysUserTokenEntity tokenEntity = baseMapper.selectOne(new LambdaQueryWrapper<SysUserTokenEntity>().eq(SysUserTokenEntity::getUserId, userId));
        if (tokenEntity == null) {
            baseMapper.insert(entity);
        } else {
            // One session per user: the row is overwritten, so also end the previous session in the cache
            tokenStoreCache.deleteUser(tokenEntity.getAccessToken());

            entity.setId(tokenEntity.getId());
            baseMapper.updateById(entity);
        }

        return BeanUtil.copyProperties(entity, SysUserTokenVO.class);
    }

    @Override
    public SysUserTokenVO refreshToken(String refreshToken) {
        LambdaQueryWrapper<SysUserTokenEntity> query = Wrappers.lambdaQuery();
        query.eq(SysUserTokenEntity::getRefreshToken, refreshToken);
        query.ge(SysUserTokenEntity::getRefreshTokenExpire, new Date());

        // If it does not exist, the refreshToken is wrong or has expired
        SysUserTokenEntity entity = baseMapper.selectOne(query);
        if (entity == null) {
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        // Delete cache info
        tokenStoreCache.deleteUser(entity.getAccessToken());

        // The user must still exist (deleted users are filtered by logic delete) and be enabled
        SysUserEntity user = sysUserDao.selectById(entity.getUserId());
        UserDetail userDetail = user == null ? null : BeanUtil.copyProperties(user, UserDetail.class);
        if (userDetail != null) {
            sysUserDetailsService.getUserDetails(userDetail);
        }
        if (userDetail == null || !userDetail.isEnabled()) {
            expireToken(entity.getUserId());
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        // Generate a new accessToken
        String accessToken = TokenUtils.generator();
        entity.setAccessToken(accessToken);
        entity.setAccessTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(new Date(), securityProperties.getAccessTokenExpire())));

        // Update
        baseMapper.updateById(entity);

        // Save user info to cache
        tokenStoreCache.saveUser(accessToken, userDetail);

        return BeanUtil.copyProperties(entity, SysUserTokenVO.class);
    }

    @Override
    public void expireToken(Long userId) {
        SysUserTokenEntity entity = new SysUserTokenEntity();
        LocalDateTime now = LocalDateTime.now();
        entity.setAccessTokenExpire(now);
        entity.setRefreshTokenExpire(now);

        baseMapper.update(entity, new LambdaQueryWrapper<SysUserTokenEntity>().eq(SysUserTokenEntity::getUserId, userId));
    }

    @Override
    public void forceLogout(String accessToken) {
        tokenStoreCache.deleteUser(accessToken);

        // Expire the stored tokens too, otherwise the client simply refreshes and is back in
        SysUserTokenEntity entity = new SysUserTokenEntity();
        LocalDateTime now = LocalDateTime.now();
        entity.setAccessTokenExpire(now);
        entity.setRefreshTokenExpire(now);

        baseMapper.update(entity, new LambdaQueryWrapper<SysUserTokenEntity>().eq(SysUserTokenEntity::getAccessToken, accessToken));
    }

    @Override
    public void logoutByUserId(Long userId) {
        baseMapper.getOnlineAccessTokenListByUserId(userId, LocalDateTime.now()).forEach(tokenStoreCache::deleteUser);

        expireToken(userId);
    }

    @Async
    @Override
    public void updateCacheAuthByRoleId(Long roleId) {
        // Query the access_token list of users by role ID
        List<String> accessTokenList = baseMapper.getOnlineAccessTokenListByRoleId(roleId, LocalDateTime.now());

        accessTokenList.forEach(this::updateCacheAuth);
    }

    @Async
    @Override
    public void updateCacheAuthByUserId(Long userId) {
        // Query the access_token list of the user by user ID
        List<String> accessTokenList = baseMapper.getOnlineAccessTokenListByUserId(userId, LocalDateTime.now());

        accessTokenList.forEach(this::updateCacheAuth);
    }

    /**
     * Update the user permissions in the Cache by accessToken
     *
     * @param accessToken access_token
     */
    private void updateCacheAuth(String accessToken) {
        UserDetail user = tokenStoreCache.getUser(accessToken);
        // User does not exist
        if (user == null) {
            return;
        }

        // Query expiration time
        Long expire = tokenStoreCache.getExpire(accessToken);
        if (expire == null) {
            return;
        }

        // Set user permission info
        sysUserDetailsService.getUserDetails(user);
        // Update cache
        tokenStoreCache.saveUser(accessToken, user, expire);

    }
}