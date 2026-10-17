package net.maku.framework.common.utils;

import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Get real address
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
public class AddressUtils {
    // Real-time query
    public static final String ADDRESS_URL = "https://whois.pconline.com.cn/ipJson.jsp";
    public static final String UNKNOWN = "Unknown";

    public static String getAddressByIP(String ip) {
        // Intranet
        if (IpUtils.internalIp(ip)) {
            return "Intranet IP";
        }

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ip", ip);
            paramMap.put("json", true);
            String response = HttpUtil.get(ADDRESS_URL, paramMap);
            if (StringUtils.isBlank(response)) {
                log.error("Failed to get the address by IP {}", ip);
                return UNKNOWN;
            }

            Address address = JsonUtils.parseObject(response, Address.class);
            return String.format("%s %s", address.getPro(), address.getCity());
        } catch (Exception e) {
            log.error("Failed to get the address by IP {}", ip);
        }

        return UNKNOWN;
    }

    @Data
    static class Address {
        /**
         * Province
         */
        private String pro;
        /**
         * City
         */
        private String city;
    }
}
