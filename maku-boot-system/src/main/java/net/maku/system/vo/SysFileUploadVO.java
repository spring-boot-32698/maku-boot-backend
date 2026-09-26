package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * File upload
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "File upload")
public class SysFileUploadVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "File name")
    private String name;

    @Schema(description = "File address")
    private String url;

    @Schema(description = "File size")
    private Long size;

    @Schema(description = "Storage platform")
    private String platform;
}
