package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Third-party login
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */

@Data
@TableName("sys_third_login")
public class SysThirdLoginEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Open platform type
     */
    private String openType;

    /**
     * Open platform, unique identifier
     */
    private String openId;

    /**
     * Nickname
     */
    private String username;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Tenant ID
     */
    private Long tenantId;

    /**
     * Version number
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * Delete flag
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}