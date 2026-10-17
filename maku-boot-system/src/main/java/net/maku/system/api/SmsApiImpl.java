package net.maku.system.api;

import lombok.AllArgsConstructor;
import net.maku.api.module.system.SmsApi;
import net.maku.sms.service.SmsService;
import net.maku.system.cache.SmsSendCache;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * SMS service Api
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Component
@AllArgsConstructor
public class SmsApiImpl implements SmsApi {
    private final SmsService smsService;
    private final SmsSendCache smsSendCache;

    @Override
    public boolean send(String mobile, Map<String, String> params) {
        return smsService.send(mobile, params);
    }

    @Override
    public boolean send(String groupName, String mobile, Map<String, String> params) {
        return smsService.send(groupName, mobile, params);
    }

    @Override
    public boolean sendCode(String mobile, String key, String value) {
        // SMS parameters
        Map<String, String> params = new HashMap<>();
        params.put(key, value);

        // Send SMS
        boolean flag = smsService.send(mobile, params);
        if (flag) {
            smsSendCache.saveCode(mobile, value);
        }
        return flag;
    }

    @Override
    public boolean sendCode(String groupName, String mobile, String key, String value) {
        // SMS parameters
        Map<String, String> params = new HashMap<>();
        params.put(key, value);

        // Send SMS
        boolean flag = smsService.send(groupName, mobile, params);
        if (flag) {
            smsSendCache.saveCode(mobile, value);
        }
        return flag;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        String value = smsSendCache.getCode(mobile);
        if (value != null) {
            // Delete SMS verification code
            smsSendCache.deleteCode(mobile);

            // Validate
            return value.equalsIgnoreCase(code);
        }

        return false;
    }
}
