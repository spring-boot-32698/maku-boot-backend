package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.utils.DateUtils;
import net.maku.framework.common.utils.TreeNode;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

/**
 * Menu management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Menu")
public class SysMenuVO extends TreeNode<SysMenuVO> {

    @Schema(description = "Menu name")
    @NotBlank(message = "Menu name cannot be empty")
    private String name;

    @Schema(description = "Menu URL")
    private String url;

    @Schema(description = "Type  0: menu   1: button   2: API")
    @Range(min = 0, max = 2, message = "Type is incorrect")
    private Integer type;

    @Schema(description = "Open mode   0: internal   1: external")
    @Range(min = 0, max = 1, message = "Open mode is incorrect")
    private Integer openStyle;

    @Schema(description = "Menu icon")
    private String icon;

    @Schema(description = "Authorization identifier(Separate multiple values with commas, e.g.: sys:menu:list,sys:menu:save)")
    private String authority;

    @Schema(description = "Sort")
    @Min(value = 0, message = "Sort value cannot be less than 0")
    private Integer sort;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "Parent menu name")
    private String parentName;
}