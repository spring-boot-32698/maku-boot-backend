package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SMS log
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "SMS log")
public class SysSmsLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Platform ID")
    private Long platformId;

    @Schema(description = "Platform type")
    private Integer platform;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Status  0: failed   1: succeeded")
    private Integer status;

    @Schema(description = "Parameter")
    private String params;

    @Schema(description = "Exception info")
    private String error;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}