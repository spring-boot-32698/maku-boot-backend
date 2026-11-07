package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Post management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Post management")
public class SysPostVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Post code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Post code cannot be empty")
    private String postCode;

    @Schema(description = "Post name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Post name cannot be empty")
    private String postName;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "Sort value cannot be less than 0")
    private Integer sort;

    @Schema(description = "Status  0: disabled   1: normal", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 1, message = "Status is incorrect")
    private Integer status;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}