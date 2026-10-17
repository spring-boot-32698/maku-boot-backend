package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * User avatar
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "User avatar")
public class SysUserAvatarVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Avatar cannot be empty")
    @Schema(description = "Avatar")
    private String avatar;

}
