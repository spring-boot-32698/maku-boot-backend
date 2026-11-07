package net.maku.quartz.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Scheduled task
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@TableName("schedule_job")
public class ScheduleJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Task name
     */
    private String jobName;

    /**
     * Task group name
     */
    private String jobGroup;

    /**
     * beanName
     */
    private String beanName;

    /**
     * Execution method
     */
    private String method;

    /**
     * Method parameters
     */
    private String params;

    /**
     * cronExpression
     */
    private String cronExpression;

    /**
     * Status
     */
    private Integer status;

    /**
     * Whether concurrent  0: forbidden  1: allowed
     */
    private Integer concurrent;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Updater
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    /**
     * Update time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

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

}