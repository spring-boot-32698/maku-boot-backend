package net.maku.iot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Device event log query
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Device event log query")
public class IotDeviceEventLogQuery extends Query {

    @Schema(description = "Command")
    private String eventTypeEnum;

    @Schema(description = "Device id")
    private Long deviceId;
}