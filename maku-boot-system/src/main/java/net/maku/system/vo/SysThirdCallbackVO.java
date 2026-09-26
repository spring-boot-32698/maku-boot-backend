package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Third-party login callback parameters
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Third-party login callback parameters")
public class SysThirdCallbackVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Open platform type")
    private String openType;

    @Schema(description = "Open platform Code")
    private String code;

    @Schema(description = "state")
    private String state;
}
