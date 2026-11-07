package net.maku.quartz.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
* Scheduled task query
*
* @author Amu babamu@126.com
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Scheduled task query")
public class ScheduleJobQuery extends Query {
    @Schema(description = "Task name")
    private String jobName;

    @Schema(description = "Task group name")
    private String jobGroup;

    @Schema(description = "Status")
    private Integer status;

}