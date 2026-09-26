package net.maku.framework.security.third;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Third-party login, ThirdUserDetailsService
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface ThirdUserDetailsService {

    /**
     * Load user info by open platform type and unique identifier
     *
     * @param openType Open platform type
     * @param openId   Open platform unique identifier
     * @return User info
     * @throws UsernameNotFoundException Non-existence exception
     */
    UserDetails loadUserByOpenTypeAndOpenId(String openType, String openId) throws UsernameNotFoundException;
}
