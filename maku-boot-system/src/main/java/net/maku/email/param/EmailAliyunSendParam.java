package net.maku.email.param;

import lombok.Data;

/**
 * Alibaba Cloud send mail parameters
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class EmailAliyunSendParam {
    /**
     * Group name, optional
     */
    private String groupName;
    /**
     * Sender email
     */
    private String from;
    /**
     * Sender nickname
     */
    private String formAlias;
    /**
     * Recipient email list, joined by commas
     */
    private String tos;
    /**
     * Mail subject
     */
    private String subject;
    /**
     * Mail content
     */
    private String content;
    /**
     * Whether it is html format
     */
    private boolean html;
}
