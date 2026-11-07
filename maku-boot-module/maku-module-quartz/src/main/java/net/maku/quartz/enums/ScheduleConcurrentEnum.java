package net.maku.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Scheduled task concurrency enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum ScheduleConcurrentEnum {
    /**
     * Forbid
     */
    NO(0),
    /**
     * Allow
     */
    YES(1);

    private final int value;
}
