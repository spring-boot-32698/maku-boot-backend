package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Super administrator enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum SuperAdminEnum {
    /**
     * Yes
     */
    YES(1, "Yes"),
    /**
     * No
     */
    NO(0, "No");

    private final Integer value;
    private final String name;

    public static String getNameByValue(int value) {
        for (SuperAdminEnum s : SuperAdminEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (SuperAdminEnum s : SuperAdminEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }
}