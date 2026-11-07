package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.maku.api.module.system.SmsApi;
import net.maku.framework.common.cache.RedisCache;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.HttpContextUtils;
import net.maku.framework.common.utils.IpUtils;
import net.maku.framework.security.cache.TokenStoreCache;
import net.maku.framework.security.crypto.Sm2Util;
import net.maku.framework.security.mobile.MobileAuthenticationToken;
import net.maku.framework.security.third.ThirdAuthenticationToken;
import net.maku.framework.security.third.ThirdLogin;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.enums.LoginOperationEnum;
import net.maku.system.service.*;
import net.maku.system.vo.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Permission authentication service
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    /**
     * SMS verification code limits: one code per mobile per interval, and at most SMS_IP_MAX requests per IP per window
     */
    private static final String SMS_MOBILE_LIMIT_KEY = "sys:sms:limit:mobile:";
    private static final String SMS_IP_LIMIT_KEY = "sys:sms:limit:ip:";
    private static final long SMS_MOBILE_INTERVAL = 60;
    private static final long SMS_IP_WINDOW = 60 * 60;
    private static final long SMS_IP_MAX = 10;

    private final SysCaptchaService sysCaptchaService;
    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SmsApi smsApi;
    private final RedisCache redisCache;

    @Override
    public SysUserTokenVO loginByAccount(SysAccountLoginVO login) {
        // Captcha validation
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // Save login log
            sysLogLoginService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());

            throw new ServerException("Wrong captcha");
        }

        Authentication authentication;
        try {
            // User authentication
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), Sm2Util.decrypt(login.getPassword())));
        } catch (BadCredentialsException e) {
            throw new ServerException("Wrong username or password");
        }

        // User info
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // Generate accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // Save user info to cache
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginByMobile(SysMobileLoginVO login) {
        Authentication authentication;
        try {
            // User authentication
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new ServerException("Mobile number or verification code is wrong");
        }

        // User info
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // Generate accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // Save user info to cache
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginByThird(SysThirdCallbackVO login) {
        Authentication authentication;
        try {
            // Convert object
            ThirdLogin thirdLogin = BeanUtil.copyProperties(login, ThirdLogin.class);

            // User authentication
            authentication = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
        } catch (BadCredentialsException e) {
            throw new ServerException("Third-party login failed");
        }

        // User info
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // Generate accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // Save user info to cache
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public boolean sendCode(String mobile) {
        // Rate limits (checked before the account lookup, so they don't reveal which numbers exist)
        if (!redisCache.setIfAbsent(SMS_MOBILE_LIMIT_KEY + mobile, 1, SMS_MOBILE_INTERVAL)) {
            throw new ServerException("Please wait " + SMS_MOBILE_INTERVAL + " seconds before requesting a new code");
        }
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request != null) {
            String ipKey = SMS_IP_LIMIT_KEY + IpUtils.getIpAddr(request);
            Long count = redisCache.increment(ipKey);
            if (count != null && count == 1) {
                redisCache.expire(ipKey, SMS_IP_WINDOW);
            }
            if (count != null && count > SMS_IP_MAX) {
                throw new ServerException("Too many verification code requests, please try again later");
            }
        }

        // Unknown number: answer exactly like a successful send, so callers can't enumerate accounts
        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            return true;
        }

        // Generate a 6-digit verification code
        String code = RandomUtil.randomNumbers(6);

        // Send SMS; if sending fails, lift the per-mobile limit so the user can retry right away
        boolean sent = false;
        try {
            sent = smsApi.sendCode(mobile, "code", code);
            return sent;
        } finally {
            if (!sent) {
                redisCache.delete(SMS_MOBILE_LIMIT_KEY + mobile);
            }
        }
    }

    @Override
    public AccessTokenVO getAccessToken(String refreshToken) {
        SysUserTokenVO token = sysUserTokenService.refreshToken(refreshToken);

        // Wrap AccessToken
        AccessTokenVO accessToken = new AccessTokenVO();
        accessToken.setAccessToken(token.getAccessToken());
        accessToken.setAccessTokenExpire(token.getAccessTokenExpire());

        return accessToken;
    }

    @Override
    public void logout(String accessToken) {
        // User info
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // Delete user info
        tokenStoreCache.deleteUser(accessToken);

        // Session already gone (expired or removed meanwhile): nothing else to do
        if (user == null) {
            return;
        }

        // TokenExpired
        sysUserTokenService.expireToken(user.getId());

        // Save login log
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }
}
