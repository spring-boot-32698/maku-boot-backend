package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Operation log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Operation log")
public class SysLogOperateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "Operator")
    private String realName;

    @Schema(description = "Module name")
    private String module;

    @Schema(description = "Operation name")
    private String name;

    @Schema(description = "Request URI")
    private String reqUri;

    @Schema(description = "Request method")
    private String reqMethod;

    @Schema(description = "Request parameters")
    private String reqParams;

    @Schema(description = "Operation IP")
    private String ip;

    @Schema(description = "Login location")
    private String address;

    @Schema(description = "User Agent")
    private String userAgent;

    @Schema(description = "Operation type")
    private Integer operateType;

    @Schema(description = "Execution duration")
    private Integer duration;

    @Schema(description = "Operation status")
    private Integer status;

    @Schema(description = "Return message")
    private String resultMsg;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}