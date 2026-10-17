package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Login info
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum LoginOperationEnum {
    /**
     * Login succeeded
     */
    LOGIN_SUCCESS(0),
    /**
     * Logout succeeded
     */
    LOGOUT_SUCCESS(1),
    /**
     * Wrong captcha
     */
    CAPTCHA_FAIL(2),
    /**
     * Wrong account or password
     */
    ACCOUNT_FAIL(3);

    private final int value;
}
