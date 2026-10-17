package net.maku.system.cache;

import lombok.AllArgsConstructor;
import net.maku.framework.common.cache.RedisCache;
import org.springframework.stereotype.Service;

/**
 * SMS sending Cache
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SmsSendCache {
    private final RedisCache redisCache;

    /**
     * Get the KEY for sending mobile SMS verification codes
     *
     * @param mobile Mobile number
     * @return KEY
     */
    private String getCodeKey(String mobile) {
        return "sys:sms:code" + mobile;
    }

    public void saveCode(String mobile, String code) {
        String key = getCodeKey(mobile);

        // Save to Redis, valid for 10 minutes
        redisCache.set(key, code, 10 * 60);
    }

    public String getCode(String mobile) {
        String key = getCodeKey(mobile);
        return (String) redisCache.get(key);
    }

    public void deleteCode(String mobile) {
        String key = getCodeKey(mobile);
        redisCache.delete(key);
    }
}
