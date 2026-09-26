package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Parameter management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Parameter management")
public class SysParamsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Parameter name")
    private String paramName;

    @Schema(description = "System parameters")
    private Integer paramType;

    @Schema(description = "Parameter key")
    private String paramKey;

    @Schema(description = "Parameter value")
    private String paramValue;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Version number")
    private Integer version;

    @Schema(description = "Delete flag")
    private Integer deleted;

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