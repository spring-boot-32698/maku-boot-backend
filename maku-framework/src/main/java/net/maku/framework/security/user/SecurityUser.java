package net.maku.framework.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class SecurityUser {

    /**
     * Get user info
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    /**
     * Get user ID
     */
    public static Long getUserId() {
        UserDetail user = getUser();
        if (user == null) {
            return null;
        }

        return user.getId();
    }

    /**
     * Get organization ID
     */
    public static Long getOrgId() {
        UserDetail user = getUser();
        if (user == null) {
            return null;
        }

        return user.getOrgId();
    }

}