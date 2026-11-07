package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mail platform enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum MailPlatformEnum {
    /**
     * Local
     */
    LOCAL(-1),
    /**
     * Alibaba Cloud
     */
    ALIYUN(0);

    private final int value;

}
