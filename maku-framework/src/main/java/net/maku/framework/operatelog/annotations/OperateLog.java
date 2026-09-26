package net.maku.framework.operatelog.annotations;

import net.maku.framework.operatelog.enums.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operation log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
    /**
     * Module name
     */
    String module() default "";

    /**
     * Operation name
     */
    String name() default "";

    /**
     * Operation type
     */
    OperateTypeEnum[] type() default OperateTypeEnum.OTHER;
}
