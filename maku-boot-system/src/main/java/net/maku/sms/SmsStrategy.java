package net.maku.sms;

import java.util.Map;

/**
 * SMS
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SmsStrategy {

    /**
     * Send SMS
     *
     * @param mobile Mobile number
     * @param params Parameter
     */
    void send(String mobile, Map<String, String> params);
}
