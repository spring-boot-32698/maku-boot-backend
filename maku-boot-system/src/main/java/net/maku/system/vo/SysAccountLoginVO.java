package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Account login
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Account login")
public class SysAccountLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Password")
    private String password;

    @Schema(description = "Unique key")
    private String key;

    @Schema(description = "Captcha")
    private String captcha;
}
