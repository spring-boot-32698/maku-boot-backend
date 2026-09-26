package net.maku.iot.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Device property enum
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DevicePropertyEnum {
    /**
     * Running status
     */
    RUNNING_STATUS(1),

    /**
     * APPVersion
     */
    APP_VERSION(2),

    /**
     * Battery level percentage
     */
    BATTERY_PERCENT(3),

    /**
     * Temperature
     */
    TEMPERATURE(4);

    /**
     * Type value
     */
    private final Integer value;

}
