package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Attachment management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Attachment management")
public class SysAttachmentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Attachment name")
    private String name;

    @Schema(description = "Attachment address")
    private String url;

    @Schema(description = "Attachment size")
    private Long size;

    @Schema(description = "Storage platform")
    private String platform;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}