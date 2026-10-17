package net.maku.framework.security.mobile;

/**
 * SMS login, verification code validation
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}
