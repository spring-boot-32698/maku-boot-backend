package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Dictionary data
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Dictionary data")
public class SysDictDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Dictionary type ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Dictionary type ID cannot be empty")
    private Long dictTypeId;

    @Schema(description = "Dictionary label", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Dictionary label cannot be empty")
    private String dictLabel;

    @Schema(description = "Label style")
    private String labelClass;

    @Schema(description = "Dictionary value")
    private String dictValue;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "Sort value cannot be less than 0")
    private Integer sort;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "Update time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;
}
