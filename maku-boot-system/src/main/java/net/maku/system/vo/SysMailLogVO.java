package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Mail log
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "Mail log")
public class SysMailLogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Platform ID")
    private Long platformId;

    @Schema(description = "Platform type")
    private Integer platform;

    @Schema(description = "Sender email")
    private String mailFrom;

    @Schema(description = "Recipient email")
    private String mailTos;

    @Schema(description = "Mail subject")
    private String subject;

    @Schema(description = "Mail content")
    private String content;

    @Schema(description = "Status  0: failed   1: succeeded")
    private Integer status;

    @Schema(description = "Exception info")
    private String error;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}