package net.maku.email.param;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * Local send mail parameters
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class EmailLocalSendParam {
    /**
     * Group name, optional
     */
    private String groupName;
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
    /**
     * Attachment list
     */
    private List<File> files = CollectionUtil.newArrayList();
}
