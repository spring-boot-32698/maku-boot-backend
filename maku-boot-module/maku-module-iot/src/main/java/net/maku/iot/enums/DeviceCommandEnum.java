package net.maku.iot.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Enum of commands sent by the server
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DeviceCommandEnum {

    /**
     * Remote lock
     */
    LOCK(1, "Remote lock", DeviceEventTypeEnum.LOCK),

    /**
     * Remote unlock
     */
    UNLOCK(2, "Remote unlock", DeviceEventTypeEnum.UNLOCK),

    /**
     * OTAUpgrade
     */
    OTA_UPGRADE(3, "OTAUpgrade", DeviceEventTypeEnum.OTA_UPGRADE);

    /**
     * Type value
     */
    private final Integer value;

    /**
     * Display name
     */
    private final String title;


    /**
     * Corresponding device event log type
     */
    private final DeviceEventTypeEnum eventType;

    /**
     * Parse the specified valueName
     *
     * @param valueName
     * @return DeviceCommandEnum
     * @throws IllegalArgumentException
     */
    public static DeviceCommandEnum parse(String valueName) {
        return Arrays.stream(DeviceCommandEnum.values())
                .filter(d -> d.name().equalsIgnoreCase(valueName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        StrUtil.format("Invalid DeviceCommandEnum value:{}", valueName)));
    }

    /**
     * Get the corresponding enum by value
     *
     * @param value
     * @return DeviceCommandEnum
     */
    public static DeviceCommandEnum getEnum(Integer value) {
        for (DeviceCommandEnum commandEnum : DeviceCommandEnum.values()) {
            if (commandEnum.getValue().equals(value)) {
                return commandEnum;
            }
        }
        return null;
    }
}

