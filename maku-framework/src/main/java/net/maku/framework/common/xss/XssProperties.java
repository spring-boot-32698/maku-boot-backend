package net.maku.framework.common.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * XSS Settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@ConfigurationProperties(prefix = "maku.xss")
public class XssProperties {
    /**
     * Whether to enable XSS
     */
    private boolean enabled;
    /**
     * List of excluded URLs
     */
    private List<String> excludeUrls = Collections.emptyList();
}
