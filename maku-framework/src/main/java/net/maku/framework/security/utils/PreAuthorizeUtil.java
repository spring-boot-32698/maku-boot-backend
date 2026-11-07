package net.maku.framework.security.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SpringSecurity @PreAuthorize Annotation permission identifier
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class PreAuthorizeUtil {

    /**
     * Get @PreAuthorize List of annotated permission identifiers
     *
     * @return Permission identifier list
     */
    public static List<String> getPreAuthorizeList() {
        List<String> authorities = new ArrayList<>();
        ApplicationContext context = SpringUtil.getApplicationContext();
        String[] beanNames = context.getBeanNamesForAnnotation(Controller.class);
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
                if (preAuthorize != null) {
                    String value = preAuthorize.value();
                    // Extract permission strings with a regular expression
                    Pattern pattern = Pattern.compile("hasAuthority\\('([^']*)'\\)");
                    Matcher matcher = pattern.matcher(value);
                    while (matcher.find()) {
                        String authority = matcher.group(1);
                        authorities.add(authority);
                    }
                }
            }
        }

        return authorities;
    }

}