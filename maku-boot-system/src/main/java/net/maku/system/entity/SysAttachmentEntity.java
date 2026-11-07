package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Attachment management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_attachment")
public class SysAttachmentEntity extends BaseEntity {
    /**
     * Attachment name
     */
    private String name;

    /**
     * Attachment address
     */
    private String url;

    /**
     * Attachment size
     */
    @TableField("size_")
    private Long size;

    /**
     * Storage platform
     */
    private String platform;
    /**
     * Tenant ID
     */
    private Long tenantId;

}