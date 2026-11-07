package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * User basic info
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "User basic info")
public class SysUserBaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be empty")
    private String realName;

    @Schema(description = "Avatar")
    private String avatar;

    @Schema(description = "Gender 0: male   1: female   2: unknown", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 2, message = "Gender is incorrect")
    private Integer gender;

    @Schema(description = "Email")
    @Email(message = "Email format is incorrect")
    private String email;

    @Schema(description = "Mobile number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Mobile number cannot be empty")
    private String mobile;

}
