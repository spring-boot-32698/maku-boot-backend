package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import net.maku.api.module.system.SmsApi;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
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
    private final SysCaptchaService sysCaptchaService;
    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SmsApi smsApi;

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
        // Generate a 6-digit verification code
        String code = RandomUtil.randomNumbers(6);

        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            throw new ServerException("Mobile number is not registered");
        }

        // Send SMS
        return smsApi.sendCode(mobile, "code", code);
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

        // TokenExpired
        sysUserTokenService.expireToken(user.getId());

        // Save login log
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }
}
