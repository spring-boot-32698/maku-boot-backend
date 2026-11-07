package net.maku.api.module.system;

/**
 * User session (token) API
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface UserTokenApi {

    /**
     * Force logout: end the session and expire both access and refresh token
     *
     * @param accessToken access_token
     */
    void forceLogout(String accessToken);
}
