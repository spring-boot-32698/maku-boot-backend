package net.maku.system.service;

import net.maku.framework.security.user.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserDetailsService {

    /**
     * Get the UserDetails object and set the user permission info
     */
    UserDetails getUserDetails(UserDetail userDetail);
}
