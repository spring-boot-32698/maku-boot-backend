package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * SMS platform
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_sms_config")
public class SysSmsConfigEntity extends BaseEntity {
    /**
     * Platform type  0: Alibaba Cloud   1: Tencent Cloud   2: Qiniu Cloud    3: Huawei Cloud
     */
    private Integer platform;

    /**
     * Group name; a group can be specified when sending SMS
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

    /**
     * Status  0: disabled   1: enabled
     */
    private Integer status;

}