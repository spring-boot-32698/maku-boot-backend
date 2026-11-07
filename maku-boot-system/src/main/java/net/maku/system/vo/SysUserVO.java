package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;
import net.maku.system.entity.SysOrgEntity;
import net.maku.framework.common.trans.Trans;
import net.maku.framework.common.trans.TransType;
import net.maku.framework.common.trans.TransPojo;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "User")
public class SysUserVO implements Serializable, TransPojo {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be empty")
    private String realName;

    @Schema(description = "Avatar")
    private String avatar;

    @Schema(description = "Signature")
    private String signature;

    @Schema(description = "Gender 0: male   1: female   2: unknown", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 2, message = "Gender is incorrect")
    private Integer gender;

    @Schema(description = "Email")
    @Email(message = "Email format is incorrect")
    private String email;

    @Schema(description = "Mobile number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Mobile number cannot be empty")
    private String mobile;

    @Schema(description = "Organization ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Organization ID cannot be empty")
    @Trans(type = TransType.SIMPLE, target = SysOrgEntity.class, fields = "name", ref = "orgName")
    private Long orgId;

    @Schema(description = "Direct supervisor ID")
    private Long leaderId;

    @Schema(description = "Status 0: disabled    1: normal", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 1, message = "User status is incorrect")
    private Integer status;

    @Schema(description = "Role ID list")
    private List<Long> roleIdList;

    @Schema(description = "Post ID list")
    private List<Long> postIdList;

    @Schema(description = "Post name list")
    private List<String> postNameList;

    @Schema(description = "Super administrator   0: no   1: yes")
    private Integer superAdmin;

    @Schema(description = "Organization name")
    private String orgName;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;
}
