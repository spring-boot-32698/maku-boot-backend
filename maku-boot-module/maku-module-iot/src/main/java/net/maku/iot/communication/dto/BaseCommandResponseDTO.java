package net.maku.iot.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Base class of response messages
 *
 * @author eden on 2024/6/17
 */
@Data
public class BaseCommandResponseDTO extends BaseDeviceID {
    /**
     * Command ID
     */
    @Schema(description = "Command ID", required = true)
    protected String commandId;
}