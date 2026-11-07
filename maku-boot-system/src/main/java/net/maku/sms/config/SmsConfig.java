package net.maku.sms.config;

import lombok.Data;

/**
 * SMS settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class SmsConfig {
    /**
     * Platform ID
     */
    private Long id;

    /**
     * Platform type
     */
    private Integer platform;

    /**
     * Group name
     */
    private String groupName;

    /**
     * SMS signature
     */
    private String signName;

    /**
     * SMS template
     */
    private String templateId;

    /**
     * ID of the SMS application, e.g.: Tencent Cloud
     */
    private String appId;

    /**
     * Required for Tencent Cloud international SMS, Huawei Cloud, etc.
     */
    private String senderId;

    /**
     * Access address, e.g.: Huawei Cloud
     */
    private String url;

    /**
     * AccessKey
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

}
