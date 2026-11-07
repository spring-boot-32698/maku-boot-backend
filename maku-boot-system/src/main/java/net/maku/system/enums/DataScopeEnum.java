package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data scope enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {
    /**
     * All data
     */
    ALL(0),
    /**
     * This organization and sub-organizations data
     */
    ORG_AND_CHILD(1),
    /**
     * This organization data
     */
    ORG_ONLY(2),
    /**
     * Own data
     */
    SELF(3),
    /**
     * Custom data
     */
    CUSTOM(4);

    private final Integer value;

}