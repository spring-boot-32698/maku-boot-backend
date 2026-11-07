package net.maku.sms;

import net.maku.framework.common.exception.ServerException;
import net.maku.sms.config.SmsConfig;
import net.maku.system.enums.SmsPlatformEnum;

import java.util.Map;

/**
 * SMS Context
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class SmsContext {
    private final SmsStrategy smsStrategy;

    public SmsContext(SmsConfig config) {
        if (config.getPlatform() == SmsPlatformEnum.ALIYUN.getValue()) {
            this.smsStrategy = new AliyunSmsStrategy(config);
        } else if (config.getPlatform() == SmsPlatformEnum.TENCENT.getValue()) {
            this.smsStrategy = new TencentSmsStrategy(config);
        } else if (config.getPlatform() == SmsPlatformEnum.QINIU.getValue()) {
            this.smsStrategy = new QiniuSmsStrategy(config);
        } else if (config.getPlatform() == SmsPlatformEnum.HUAWEI.getValue()) {
            this.smsStrategy = new HuaweiSmsStrategy(config);
        } else {
            throw new ServerException("Unknown SMS platform");
        }
    }

    public void send(String mobile, Map<String, String> params) {
        smsStrategy.send(mobile, params);
    }
}
