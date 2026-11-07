package net.maku.framework.security.third;

import lombok.Data;

import java.io.Serializable;

/**
 * Third-party login form data
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class ThirdLogin implements Serializable {
    /**
     * Open platform type
     */
    private String openType;

    /**
     * Open platform Code
     */
    private String code;

    /**
     * state
     */
    private String state;
}
