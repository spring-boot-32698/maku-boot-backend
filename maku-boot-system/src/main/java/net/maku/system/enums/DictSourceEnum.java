package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Dictionary data source
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum DictSourceEnum {
    /**
     * Dictionary data
     */
    DICT(0),
    /**
     * Dynamic SQL
     */
    SQL(1);

    private final int value;
}
