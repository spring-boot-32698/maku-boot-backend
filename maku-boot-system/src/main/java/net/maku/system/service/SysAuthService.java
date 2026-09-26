package net.maku.system.service;

import net.maku.system.vo.*;

/**
 * Permission authentication service
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysAuthService {

    /**
     * Account and password login
     *
     * @param login Login info
     */
    SysUserTokenVO loginByAccount(SysAccountLoginVO login);

    /**
     * SMS login
     *
     * @param login Login info
     */
    SysUserTokenVO loginByMobile(SysMobileLoginVO login);

    /**
     * Third-party login
     *
     * @param login Login info
     */
    SysUserTokenVO loginByThird(SysThirdCallbackVO login);

    /**
     * Send mobile verification code
     *
     * @param mobile Mobile number
     */
    boolean sendCode(String mobile);

    /**
     * Get AccessToken by refresh Token
     *
     * @param refreshToken refreshToken
     */
    AccessTokenVO getAccessToken(String refreshToken);

    /**
     * Log out
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken);
}
