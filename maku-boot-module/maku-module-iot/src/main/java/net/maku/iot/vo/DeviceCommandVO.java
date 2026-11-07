package net.maku.iot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Device command
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device command VO")
public class DeviceCommandVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Device ID")
    private Long deviceId;

    @Schema(description = "Command")
    private String command;

    @Schema(description = "Command content")
    private String payload;
}
