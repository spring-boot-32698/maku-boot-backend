package net.maku.iot.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Device event type enum
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DeviceEventTypeEnum {
    /**
     * Go offline
     */
    OFFLINE(0, "Offline event"),

    /**
     * Go online
     */
    ONLINE(1, "Online event"),

    /**
     * Login
     */
    SIGN_ON(2, "Login event"),

    /**
     * Log out
     */
    SIGN_OFF(3, "Logout event"),
    /**
     * OTAUpgrade
     */
    OTA_UPGRADE(4, "OTAUpgrade event"),

    /**
     * Device remote lock
     */
    LOCK(5, "Device remote lock"),

    /**
     * Device remote unlock
     */
    UNLOCK(6, "Device remote unlock"),

    /**
     * APPVersion info report
     */
    APP_VERSION_REPORT(7, "APPVersion info report"),

    /**
     * Battery level report
     */
    BATTERY_PERCENT_REPORT(8, "Battery level report"),

    /**
     * Temperature report
     */
    TEMPERATURE_REPORT(9, "Temperature report");

    /**
     * Type value
     */
    private final Integer value;

    /**
     * Type name
     */
    private final String title;


    /**
     * Parse the specified valueName
     *
     * @param valueName
     * @return DeviceEventTypeEnum
     * @throws IllegalArgumentException
     */
    public static DeviceEventTypeEnum parse(String valueName) {
        return Arrays.stream(DeviceEventTypeEnum.values())
                .filter(d -> d.name().equalsIgnoreCase(valueName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        StrUtil.format("Invalid DeviceEventTypeEnum value:{}", valueName)));
    }

    /**
     * Get the corresponding enum by value
     *
     * @param value
     * @return DeviceCommandEnum
     */
    public static DeviceEventTypeEnum getEnum(Integer value) {
        for (DeviceEventTypeEnum commandEnum : DeviceEventTypeEnum.values()) {
            if (commandEnum.getValue().equals(value)) {
                return commandEnum;
            }
        }
        return null;
    }
}
