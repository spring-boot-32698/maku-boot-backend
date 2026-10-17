package net.maku.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Scheduled task status enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum ScheduleStatusEnum {
    /**
     * Pause
     */
    PAUSE(0),
    /**
     * Normal
     */
    NORMAL(1);

    private final int value;
}
