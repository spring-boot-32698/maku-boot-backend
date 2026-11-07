package net.maku.framework.security.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import net.maku.framework.common.utils.HttpContextUtils;

/**
 * Token Utility class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class TokenUtils {

    /**
     * Generate AccessToken
     * <p>
     * Uses UUID.randomUUID() (SecureRandom): tokens are bearer credentials, so they must not be predictable.
     */
    public static String generator() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Get AccessToken
     */
    public static String getAccessToken() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return getAccessToken(request);
    }

    /**
     * Get AccessToken
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StrUtil.isBlank(accessToken)) {
            accessToken = request.getParameter("access_token");
        }

        return accessToken;
    }
}
