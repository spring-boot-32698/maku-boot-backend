package net.maku.member.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Member management
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "Member management")
public class MemberUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Nickname")
    private String nickName;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Avatar")
    private String avatar;

    @Schema(description = "Date of birth")
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    private LocalDateTime birthday;

    @Schema(description = "Gender")
    private Integer gender;

    @Schema(description = "Last login IP")
    private String lastLoginIp;

    @Schema(description = "Last login time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime lastLoginTime;

    @Schema(description = "Remarks")
    private String remark;

    @Schema(description = "Status")
    private Integer status;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}