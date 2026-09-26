package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Role data permission
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Role data permission")
public class SysRoleDataScopeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull(message = "Role ID cannot be empty")
    private Long id;

    @Schema(description = "Data scope  0: all data  1: this organization and sub-organizations  2: this organization only  3: own data  4: custom data")
    @NotNull(message = "Data scope cannot be empty")
    private Integer dataScope;

    @Schema(description = "Organization ID list")
    private List<Long> orgIdList;

}