package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SMS log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@TableName("sys_sms_log")
public class SysSmsLogEntity {
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
     * Mobile number
     */
    private String mobile;

    /**
     * Status  0: failed   1: succeeded
     */
    private Integer status;

    /**
     * Parameter
     */
    private String params;

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