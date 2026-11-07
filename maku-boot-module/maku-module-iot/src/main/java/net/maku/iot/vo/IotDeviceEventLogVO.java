package net.maku.iot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;
import net.maku.iot.enums.DeviceEventTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Device event log
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device event log")
public class IotDeviceEventLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Event type")
    private Integer eventType;

    @Schema(description = "Event")
    private DeviceEventTypeEnum eventTypeEnum;

    @Schema(description = "Event identifier id")
    private String eventUid;

    @Schema(description = "Event data")
    private String eventPayload;

    @Schema(description = "Event time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime eventTime;

}