package net.maku.iot.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Device running status enum
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DeviceRunningStatusEnum {
    /**
     * Offline status
     */
    OFFLINE(0, "Offline", DeviceEventTypeEnum.OFFLINE),

    /**
     * Online status
     */
    ONLINE(1, "Online", DeviceEventTypeEnum.ONLINE);

    /**
     * Status value
     */
    private final Integer value;

    /**
     * Status display name
     */
    private final String title;

    /**
     * Device event type
     */
    private final DeviceEventTypeEnum eventType;


    /**
     * Parse the specified valueName
     *
     * @param valueName
     * @return DeviceRunningStatusEnum
     * @throws IllegalArgumentException
     */
    public static DeviceRunningStatusEnum parse(String valueName) {
        return Arrays.stream(DeviceRunningStatusEnum.values())
                .filter(d -> d.getValue().toString().equals(valueName)
                        || d.name().equalsIgnoreCase(valueName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        StrUtil.format("Invalid DeviceRunningStatusEnum value:{}", valueName)));
    }

}
