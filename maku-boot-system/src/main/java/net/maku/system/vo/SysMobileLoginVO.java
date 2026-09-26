package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Mobile number login
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Mobile number login")
public class SysMobileLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Captcha")
    private String code;
}
