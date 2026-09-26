package net.maku.system.cache;

import lombok.AllArgsConstructor;
import net.maku.framework.common.cache.RedisCache;
import net.maku.sms.config.SmsConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SMS platform Cache
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SmsConfigCache {
    private final RedisCache redisCache;

    /**
     * SMS platform rotation KEY
     */
    private final String SMS_ROUND_KEY = "sys:sms:round";
    private final String SMS_ROUND_CODE_KEY = "sys:sms:round:code";

    /**
     * SMS platform list KEY
     */
    private final String SMS_PLATFORM_KEY = "sys:sms:platform";

    /**
     * Get the SMS rotation value
     */
    public Long getRoundValue() {
        return redisCache.increment(SMS_ROUND_KEY);
    }

    /**
     * Get the SMS code rotation value
     */
    public Long getRoundCodeValue() {
        return redisCache.increment(SMS_ROUND_CODE_KEY);
    }

    public List<SmsConfig> list() {
        return (List<SmsConfig>) redisCache.get(SMS_PLATFORM_KEY);
    }

    public void save(List<SmsConfig> list) {
        redisCache.set(SMS_PLATFORM_KEY, list);
    }

    public void delete() {
        redisCache.delete(SMS_PLATFORM_KEY);
    }
}
