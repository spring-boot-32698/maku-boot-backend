package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * User changes password
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "User changes password")
public class SysUserPasswordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Old password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Old password cannot be empty")
    private String password;

    @Schema(description = "New password, length must be 4-20 characters", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 4, max = 20, message = "New password length must be 4-20 characters")
    private String newPassword;

}