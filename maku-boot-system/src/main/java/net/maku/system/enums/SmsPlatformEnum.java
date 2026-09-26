package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS platform enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum SmsPlatformEnum {
    /**
     * Alibaba Cloud
     */
    ALIYUN(0),
    /**
     * Tencent Cloud
     */
    TENCENT(1),
    /**
     * Qiniu Cloud
     */
    QINIU(2),
    /**
     * Huawei Cloud
     */
    HUAWEI(3);

    private final int value;

}
