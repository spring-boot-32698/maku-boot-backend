package net.maku.quartz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Scheduled task
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "Scheduled task")
public class ScheduleJobVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Task name")
    private String jobName;

    @Schema(description = "Task group name")
    private String jobGroup;

    @Schema(description = "beanName")
    private String beanName;

    @Schema(description = "Execution method")
    private String method;

    @Schema(description = "Parameter")
    private String params;

    @Schema(description = "cronExpression")
    private String cronExpression;

    @Schema(description = "Status ")
    private Integer status;

    @Schema(description = "Whether concurrent")
    private Integer concurrent;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}