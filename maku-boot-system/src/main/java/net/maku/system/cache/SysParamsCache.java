package net.maku.system.cache;

import lombok.AllArgsConstructor;
import net.maku.framework.common.cache.RedisCache;
import org.springframework.stereotype.Service;

/**
 * Parameter management Cache
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysParamsCache {
    private final RedisCache redisCache;

    /**
     * Parameter management KEY
     */
    private final String SYSTEM_PARAMS_KEY = "system:params";

    /**
     * Get the parameter value by parameter key
     *
     * @param paramKey Parameter key
     */
    public String get(String paramKey) {
        return (String) redisCache.hGet(SYSTEM_PARAMS_KEY, paramKey);
    }

    /**
     * Get the parameter value by parameter key
     *
     * @param paramKey   Parameter key
     * @param paramValue Parameter value
     */
    public void save(String paramKey, String paramValue) {
        redisCache.hSet(SYSTEM_PARAMS_KEY, paramKey, paramValue, RedisCache.NOT_EXPIRE);
    }

    /**
     * Delete the parameter value by parameter key
     *
     * @param paramKey Parameter key
     */
    public void del(String... paramKey) {
        redisCache.hDel(SYSTEM_PARAMS_KEY, paramKey);
    }
}
