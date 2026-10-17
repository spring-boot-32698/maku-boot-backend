package net.maku.framework.common.trans;

import java.lang.annotation.*;

/**
 * Translation annotation, marks fields that need translation
 *
 * @author maku
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trans {
    /**
     * Translation type
     *
     * @see TransType
     */
    String type();

    /**
     * Dictionary type key (used in dictionary translation)
     */
    String key() default "";

    /**
     * Field name where the translation result is stored
     */
    String ref() default "";

    /**
     * Target entity class (used in SIMPLE translation)
     */
    Class<?> target() default Object.class;

    /**
     * Name of the field to translate (used in SIMPLE translation)
     */
    String fields() default "";
}
