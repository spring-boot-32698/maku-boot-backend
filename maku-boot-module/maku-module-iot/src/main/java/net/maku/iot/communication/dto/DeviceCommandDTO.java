package net.maku.iot.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.iot.enums.DeviceCommandEnum;

/**
 * Device command object
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device command object")
public class DeviceCommandDTO extends BaseDeviceID {
    /**
     * Command type
     */
    @Schema(description = "Command type", required = true)
    private DeviceCommandEnum command;

    /**
     * Command id
     */
    @Schema(description = "Command id", required = true)
    private String id;

    /**
     * Command content
     */
    @Schema(description = "Command content")
    private String payload;
}