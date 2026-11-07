package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Role management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Role")
public class SysRoleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Role name")
    @NotBlank(message = "Role name cannot be empty")
    private String name;

    @Schema(description = "Role code")
    @NotBlank(message = "Role code cannot be empty")
    private String roleCode;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Data scope  0: all data  1: this organization and sub-organizations  2: this organization only  3: own data  4: custom data")
    private Integer dataScope;

    @Schema(description = "Menu ID list")
    private List<Long> menuIdList;

    @Schema(description = "Organization ID list")
    private List<Long> orgIdList;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}