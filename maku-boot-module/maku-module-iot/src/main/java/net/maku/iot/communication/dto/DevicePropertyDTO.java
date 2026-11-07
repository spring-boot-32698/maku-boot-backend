package net.maku.iot.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.iot.enums.DevicePropertyEnum;

/**
 * Device property object
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device property object")
public class DevicePropertyDTO extends BaseDeviceID {
    /**
     * Device property type
     */
    @Schema(description = "Device property type")
    private DevicePropertyEnum propertyType;

    /**
     * Property data
     */
    @Schema(description = "Status data; different status types need to pass in the corresponding status data", required = true)
    private String payload;
}
