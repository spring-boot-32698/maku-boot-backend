package net.maku.framework.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Logged-in user info
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class UserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String avatar;
    private String signature;
    private Integer gender;
    private String email;
    private String mobile;
    private Long orgId;
    private Integer status;
    private Integer superAdmin;
    private Long tenantId;
    private LocalDateTime createTime;

    /**
     * Data permission scope
     * <p>
     * null: means all data permissions
     */
    private List<Long> dataScopeList;
    /**
     * Whether the account has expired
     */
    private boolean isAccountNonExpired = true;
    /**
     * Whether the account is locked
     */
    private boolean isAccountNonLocked = true;
    /**
     * Whether the password has expired
     */
    private boolean isCredentialsNonExpired = true;
    /**
     * Whether the account is available
     */
    private boolean isEnabled = true;
    /**
     * Set of granted permissions
     */
    private Set<String> authoritySet;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}