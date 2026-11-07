package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Menu type enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    /**
     * Menu
     */
    MENU(0),
    /**
     * Button
     */
    BUTTON(1),
    /**
     * API
     */
    INTERFACE(2);

    private final int value;

}
