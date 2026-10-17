package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Dictionary type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Dictionary type")
public class SysDictTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Dictionary type cannot be empty")
    private String dictType;

    @Schema(description = "Dictionary name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Dictionary name cannot be empty")
    private String dictName;

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

    @Schema(description = "Source  0: dictionary data  1: dynamic SQL")
    private Integer dictSource;

    @Schema(description = "Dynamic sql")
    private String dictSql;

    @Schema(description = "Parent node")
    private Long pid;

    @Schema(description = "Whether there are child nodes  0: none  1: yes")
    private Integer hasChild;
}