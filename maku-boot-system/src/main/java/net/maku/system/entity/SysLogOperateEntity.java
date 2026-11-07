package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Operation log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */

@Data
@TableName("sys_log_operate")
public class SysLogOperateEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Operator
     */
    private String realName;

    /**
     * Module name
     */
    private String module;

    /**
     * Operation name
     */
    private String name;

    /**
     * Request URI
     */
    private String reqUri;

    /**
     * Request method
     */
    private String reqMethod;

    /**
     * Request parameters
     */
    private String reqParams;

    /**
     * Operation IP
     */
    private String ip;

    /**
     * Login location
     */
    private String address;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * Operation type
     */
    private Integer operateType;

    /**
     * Execution duration
     */
    private Integer duration;

    /**
     * Operation status
     */
    private Integer status;

    /**
     * Return message
     */
    private String resultMsg;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Tenant ID
     */
    private Long tenantId;

}