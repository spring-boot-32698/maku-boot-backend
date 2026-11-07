package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.utils.DateUtils;
import net.maku.framework.common.utils.TreeNode;

import java.time.LocalDateTime;

/**
 * Organization list
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Organization")
public class SysOrgVO extends TreeNode<SysOrgVO> {

    @Schema(description = "Organization name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Organization name cannot be empty")
    private String name;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "Sort value cannot be less than 0")
    private Integer sort;

    @Schema(description = "Person in charge ID")
    private Long leaderId;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "Parent name")
    private String parentName;

}