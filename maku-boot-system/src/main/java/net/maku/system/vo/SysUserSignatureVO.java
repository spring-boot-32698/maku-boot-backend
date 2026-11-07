package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * User signature
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "User signature")
public class SysUserSignatureVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Signature cannot be empty")
    @Schema(description = "Signature")
    private String signature;

}
