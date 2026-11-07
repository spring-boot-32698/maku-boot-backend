package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * User gender status
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum UserGenderEnum {
    /**
     * Male
     */
    MAN(0, "Male"),
    /**
     * Female
     */
    WOMEN(1, "Female"),
    /**
     * Unknown
     */
    UNKNOWN(2,"Unknown");

    private final int value;
    private final String name;

    public static String getNameByValue(int value) {
        for (UserGenderEnum s : UserGenderEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (UserGenderEnum s : UserGenderEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }
}
