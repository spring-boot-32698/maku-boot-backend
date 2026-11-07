package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Mail log
 *
 * @author Amu babamu@126.com
 */
@Data
@TableName("sys_mail_log")
public class SysMailLogEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Platform ID
     */
    private Long platformId;

    /**
     * Platform type
     */
    private Integer platform;

    /**
     * Sender email
     */
    private String mailFrom;

    /**
     * Recipient email
     */
    private String mailTos;

    /**
     * Mail subject
     */
    private String subject;

    /**
     * Mail content
     */
    private String content;

    /**
     * Status  0: failed   1: succeeded
     */
    private Integer status;

    /**
     * Exception info
     */
    private String error;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}