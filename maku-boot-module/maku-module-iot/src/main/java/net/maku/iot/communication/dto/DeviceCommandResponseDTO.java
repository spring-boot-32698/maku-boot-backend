package net.maku.iot.communication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.iot.enums.DeviceCommandEnum;

/**
 * Device command response DTO
 *
 * @author LSF maku_lsf@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Device command response DTO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceCommandResponseDTO extends BaseCommandResponseDTO {
    /**
     * Command type
     */
    @Schema(description = "Command type", required = true)
    private DeviceCommandEnum command;

    /**
     * Whether the command is complete (default true: command completed; false: command not completed, a response message is sent again when the command completes, and the server keeps waiting for that response)
     */
    @Schema(description = "Whether the command is complete (default true: command completed; false: command not completed, a response message is sent again when the command completes, and the server keeps waiting for that response)")
    private boolean isCompleted = true;

    /**
     * Response status code, 0 means success, other values are errors, customizable per business needs
     */
    @Schema(description = "Response status code, 0 means success, other values are errors, customizable per business needs")
    private Integer statusCode = 0;
    /**
     * Command response result
     */
    @Schema(description = "Command response result")
    private String responsePayload;
}
