package net.maku.iot.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Device type enum,Newly added device type enum names are not allowed to use"_"
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DeviceTypeEnum {
    /**
     * Handheld device
     */
    HANDSET(1),

    /**
     * Cabinet
     */
    CABINET(2),

    /**
     * Sensor device
     */
    SENSOR(3);

    /**
     * Type value
     */
    private final Integer value;

    /**
     * Parse the specified valueName
     *
     * @param valueName
     * @return DeviceTypeEnum
     * @throws IllegalArgumentException
     */
    public static DeviceTypeEnum parse(String valueName) {
        return Arrays.stream(DeviceTypeEnum.values())
                .filter(d -> d.getValue().toString().equals(valueName)
                        || d.name().equalsIgnoreCase(valueName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        StrUtil.format("Invalid DeviceTypeEnum value:{}", valueName)));
    }

}
