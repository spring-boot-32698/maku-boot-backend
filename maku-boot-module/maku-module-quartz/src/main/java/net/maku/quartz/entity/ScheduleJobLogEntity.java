package net.maku.quartz.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Scheduled task log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@TableName("schedule_job_log")
public class ScheduleJobLogEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Task id
     */
    private Long jobId;

    /**
     * Task name
     */
    private String jobName;

    /**
     * Task group name
     */
    private String jobGroup;

    /**
     * spring beanName
     */
    private String beanName;

    /**
     * Execution method
     */
    private String method;

    /**
     * Parameter
     */
    private String params;

    /**
     * Task status
     */
    private Integer status;

    /**
     * Exception info
     */
    private String error;

    /**
     * Elapsed time(Unit: milliseconds)
     */
    private Long times;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}