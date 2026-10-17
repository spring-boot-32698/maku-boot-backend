package net.maku.framework.security.mobile;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * SMS login, UserDetailsService
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface MobileUserDetailsService {

    /**
     * Load user info by mobile number
     *
     * @param mobile Mobile number
     * @return User info
     * @throws UsernameNotFoundException Non-existence exception
     */
    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
