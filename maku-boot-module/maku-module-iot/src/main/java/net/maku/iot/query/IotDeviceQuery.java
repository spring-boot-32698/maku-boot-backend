package net.maku.iot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Device table query
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Device table query")
public class IotDeviceQuery extends Query {

    @Schema(description = "Status, 0 disabled, 1 normal")
    private Integer status;

    @Schema(description = "Running status, 0. offline 1. online 2. normal standby 3. in use 4. OTA upgrading")
    private Integer runningStatus;

    @Schema(description = "Device type, 1. handheld device, 2. cabinet, 3. sensor device")
    private Integer type;
}