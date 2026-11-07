package net.maku.system.service;

import net.maku.system.vo.SysCaptchaVO;

/**
 * Captcha
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysCaptchaService {
    /**
     * Generate verification code
     */
    SysCaptchaVO generate();

    /**
     * Captcha validation
     *
     * @param key  key
     * @param code Captcha
     * @return true: success  false: failure
     */
    boolean validate(String key, String code);

    /**
     * Whether to enable the login captcha
     *
     * @return true: enabled  false: disabled
     */
    boolean isCaptchaEnabled();
}
