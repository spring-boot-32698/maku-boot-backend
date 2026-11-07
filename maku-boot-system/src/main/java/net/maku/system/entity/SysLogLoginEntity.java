package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Login log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@TableName("sys_log_login")
public class SysLogLoginEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * Login IP
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
     * Login status  0: failed   1: succeeded
     */
    private Integer status;

    /**
     * Operation info   0: login succeeded   1: logout succeeded  2: wrong captcha  3: wrong account or password
     */
    private Integer operation;

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