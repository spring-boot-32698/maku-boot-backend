package net.maku.system.api;

import lombok.AllArgsConstructor;
import net.maku.api.module.system.UserTokenApi;
import net.maku.system.service.SysUserTokenService;
import org.springframework.stereotype.Component;

/**
 * User session (token) Api
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Component
@AllArgsConstructor
public class UserTokenApiImpl implements UserTokenApi {
    private final SysUserTokenService sysUserTokenService;

    @Override
    public void forceLogout(String accessToken) {
        sysUserTokenService.forceLogout(accessToken);
    }
}
