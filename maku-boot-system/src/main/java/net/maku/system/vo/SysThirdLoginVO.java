package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Third-party login
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Third-party login")
public class SysThirdLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Open platform type")
    private String openType;

    @Schema(description = "Open platform, unique identifier")
    private String openId;

    @Schema(description = "Nickname")
    private String username;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "Tenant ID")
    private Long tenantId;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;
}