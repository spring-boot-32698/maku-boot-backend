package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * SMS sending
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "SMS sending")
public class SysSmsSendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Parameter Key")
    private String paramKey;

    @Schema(description = "Parameter Value")
    private String paramValue;

}