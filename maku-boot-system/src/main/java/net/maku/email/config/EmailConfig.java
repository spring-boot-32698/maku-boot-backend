package net.maku.email.config;

import lombok.Data;

/**
 * Email settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class EmailConfig {
    /**
     * Platform ID
     */
    private Long id;

    /**
     * Platform type
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

}
