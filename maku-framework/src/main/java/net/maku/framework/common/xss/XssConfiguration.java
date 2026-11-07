package net.maku.framework.common.xss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import tools.jackson.databind.module.SimpleModule;

/**
 * XSS Configuration file
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(prefix = "maku.xss", value = "enabled")
public class XssConfiguration {
    private final static PathMatcher pathMatcher = new AntPathMatcher();

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties) {
        FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new XssFilter(properties, pathMatcher));
        bean.setOrder(Integer.MAX_VALUE);
        bean.setName("xssFilter");

        return bean;
    }

    /**
     * xssFilter module, handles json requests
     * Spring Boot 4 Module Beans are registered to JsonMapper automatically, no need to create an ObjectMapper manually
     */
    @Bean
    public SimpleModule xssFilterModule(XssProperties properties) {
        SimpleModule module = new SimpleModule("XssFilterJsonDeserializer");
        module.addDeserializer(String.class, new XssFilterJsonDeserializer(properties, pathMatcher));
        return module;
    }
}
