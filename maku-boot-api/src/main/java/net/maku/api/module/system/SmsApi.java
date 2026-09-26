package net.maku.api.module.system;

import java.util.Map;

/**
 * SMS service API
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SmsApi {

    /**
     * Send SMS
     *
     * @param mobile Mobile number
     * @param params Parameter
     * @return Whether sent successfully
     */
    boolean send(String mobile, Map<String, String> params);

    /**
     * Send SMS
     *
     * @param groupName Group name
     * @param mobile    Mobile number
     * @param params    Parameter
     * @return Whether sent successfully
     */
    boolean send(String groupName, String mobile, Map<String, String> params);

    /**
     * Send SMS
     *
     * @param mobile Mobile number
     * @param key    Parameter KEY
     * @param value  Parameter Value
     * @return Whether sent successfully
     */
    boolean sendCode(String mobile, String key, String value);

    /**
     * Send SMS
     *
     * @param groupName Group name
     * @param mobile    Mobile number
     * @param key       Parameter KEY
     * @param value     Parameter Value
     * @return Whether sent successfully
     */
    boolean sendCode(String groupName, String mobile, String key, String value);
    
    /**
     * Validate the SMS verification code
     *
     * @param mobile Mobile number
     * @param code   Captcha
     * @return Whether validation succeeded
     */
    boolean verifyCode(String mobile, String code);
}
