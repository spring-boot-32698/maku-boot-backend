package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Third-party login settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@TableName("sys_third_login_config")
public class SysThirdLoginConfigEntity {
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
     * ClientID
     */
    private String clientId;

    /**
     * ClientSecret
     */
    private String clientSecret;

    /**
     * RedirectUri
     */
    private String redirectUri;

    /**
     * AgentID
     */
    private String agentId;

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