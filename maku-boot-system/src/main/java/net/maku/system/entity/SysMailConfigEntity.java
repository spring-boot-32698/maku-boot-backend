package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Mail platform
 *
 * @author Amu babamu@126.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_mail_config")
public class SysMailConfigEntity extends BaseEntity {
    /**
     * Platform type  -1: local   0: Alibaba Cloud
     */
    private Integer platform;

    /**
     * Group name; a group can be specified when sending mail
     */
    private String groupName;

    /**
     * SMTPServer
     */
    private String mailHost;

    /**
     * SMTPPort
     */
    private Integer mailPort;

    /**
     * Sender email
     */
    private String mailFrom;

    /**
     * Sender password
     */
    private String mailPass;

    /**
     * regionId
     */
    private String regionId;

    /**
     * Alibaba Cloud endpoint
     */
    private String endpoint;

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