package net.maku.iot.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.iot.enums.DeviceCommandEnum;

import java.io.Serializable;

/**
 * Device command response data
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device command response VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceCommandResponseAttributeDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Device ID")
    private Long deviceId;

    @Schema(description = "Command type", required = true)
    private DeviceCommandEnum command;

    @Schema(description = "Command ID", required = true)
    private String commandId;

    @Schema(description = "Whether the command is complete (default true: command completed; false: command not completed, a response message is sent again when the command completes, and the server keeps waiting for that response)")
    private boolean isCompleted = true;

    @Schema(description = "Response status code, 0 means success, other values are errors, customizable per business needs")
    private Integer statusCode = 0;

    @Schema(description = "Response status message")
    private String statusMessage;

    @Schema(description = "Command response result")
    private String responsePayload;
}
