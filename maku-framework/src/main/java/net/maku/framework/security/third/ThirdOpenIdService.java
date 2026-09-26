package net.maku.framework.security.third;

/**
 * Third-party login, get the open platform user unique identifier by code
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface ThirdOpenIdService {

    /**
     * Get the open platform user unique identifier by code
     *
     * @param login Third-party login info
     * @return Open platform user unique identifier
     */
    String getOpenId(ThirdLogin login);
}
