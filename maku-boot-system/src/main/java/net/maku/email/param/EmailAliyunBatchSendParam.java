package net.maku.email.param;

import lombok.Data;

/**
 * Alibaba Cloud batch send mail parameters
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class EmailAliyunBatchSendParam {
    /**
     * Group name, optional
     */
    private String groupName;
    /**
     * Sender email
     */
    private String from;
    /**
     * Recipient list name
     */
    private String receiversName;
    /**
     * Mail template name
     */
    private String templateName;
    /**
     * Mail tag
     */
    private String tagName;
}
