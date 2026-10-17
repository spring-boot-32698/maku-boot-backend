package net.maku.framework.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Security settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "maku.security")
public class SecurityProperties {
    /**
     * accessToken Expiration time(Unit: seconds), default 2 hours
     */
    private int accessTokenExpire = 60 * 60 * 2;
    /**
     * refreshToken Expiration time(Unit: seconds), default 14 days
     */
    private int refreshTokenExpire = 60 * 60 * 24 * 14;
}
