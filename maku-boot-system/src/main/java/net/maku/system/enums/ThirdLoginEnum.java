package net.maku.system.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Third-party login enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum ThirdLoginEnum {
    /**
     * WeCom
     */
    WECHAT_WORK("wechat_work"),

    /**
     * DingTalk
     */
    DING_TALK("dingtalk"),
    /**
     * Feishu
     */
    FEI_SHU("feishu"),

    /**
     * WeChat Open Platform
     */
    WECHAT_OPEN("wechat_open");

    private final String value;

    public static ThirdLoginEnum toEnum(String value) {
        for (ThirdLoginEnum item : values()) {
            if (StrUtil.equalsIgnoreCase(item.getValue(), value)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Unsupported third login type: " + value);
    }
}