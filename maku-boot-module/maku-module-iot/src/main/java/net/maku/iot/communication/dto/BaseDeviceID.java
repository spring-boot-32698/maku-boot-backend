package net.maku.iot.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Device ID
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device ID")
public class BaseDeviceID {

    @Schema(description = "Device ID")
    protected String deviceId;
}
