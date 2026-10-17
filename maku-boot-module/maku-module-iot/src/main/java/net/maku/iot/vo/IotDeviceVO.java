package net.maku.iot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Device table
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device table")
public class IotDeviceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "Code")
    private String code;

    @Schema(description = "Name")
    private String name;

    @Schema(description = "Device type, 1. handheld device, 2. cabinet, 3. sensor device")
    private Integer type;

    @Schema(description = "Protocol type")
    private String protocolType;

    @Schema(description = "Unique identifier")
    private String uid;

    @Schema(description = "Device key")
    private String secret;

    @Schema(description = "AppVersion number")
    private String appVersion;

    @Schema(description = "Battery level percentage")
    private String batteryPercent;

    @Schema(description = "Temperature")
    private String temperature;

    @Schema(description = "Status, 0 disabled, 1 normal")
    private Integer status;

    @Schema(description = "Running status")
    private Integer runningStatus;

    @Schema(description = "Online time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime upTime;

    @Schema(description = "Offline time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime downTime;

    @Schema(description = "Tenant ID")
    private Long tenantId;

    @Schema(description = "Creator")
    private Long creator;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "Updater")
    private Long updater;

    @Schema(description = "Update time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;
    
}