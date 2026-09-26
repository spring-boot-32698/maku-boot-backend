package net.maku.iot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Device reports property data
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "Device property data report VO")
public class DeviceReportAttributeDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Device ID")
    private Long deviceId;

    @Schema(description = "Device property type")
    private String propertyType;

    @Schema(description = "Property data")
    private String payload;

}
