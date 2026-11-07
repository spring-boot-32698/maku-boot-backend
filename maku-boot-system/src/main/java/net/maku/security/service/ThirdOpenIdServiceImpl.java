package net.maku.security.service;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.security.third.ThirdLogin;
import net.maku.framework.security.third.ThirdOpenIdService;
import net.maku.system.service.SysThirdLoginConfigService;
import org.springframework.stereotype.Service;

/**
 * Third-party login, get the open platform user unique identifier by code
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class ThirdOpenIdServiceImpl implements ThirdOpenIdService {
    private final SysThirdLoginConfigService sysThirdLoginConfigService;

    @Override
    public String getOpenId(ThirdLogin login) {
        AuthRequest authRequest = sysThirdLoginConfigService.getAuthRequest(login.getOpenType());
        AuthCallback callback = AuthCallback.builder().code(login.getCode()).state(login.getState()).build();

        // Get user info by code
        AuthResponse<AuthUser> response = authRequest.login(callback);

        // Check whether it succeeded
        if (!response.ok()) {
            throw new ServerException("Third-party login failed");
        }

        return response.getData().getUuid();
    }
}
