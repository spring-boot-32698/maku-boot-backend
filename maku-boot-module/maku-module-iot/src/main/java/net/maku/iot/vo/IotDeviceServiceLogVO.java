package net.maku.iot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;
import net.maku.iot.enums.DeviceCommandEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Device service log
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device service log")
public class IotDeviceServiceLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Service type")
    private Integer serviceType;

    @Schema(description = "Command")
    private DeviceCommandEnum deviceCommandEnum;

    @Schema(description = "Service identifier id")
    private String serviceUid;

    @Schema(description = "Service data")
    private String servicePayload;

    @Schema(description = "Service time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime serviceTime;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;
}