package net.maku.websocket.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.time.LocalDateTime;


/**
 * Object that sends the message
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Tag(name = "Object that sends the message")
public class MessageVO {
    @Schema(description = "Username")
    private String name;

    @Schema(description = "User avatar")
    private String avatar;

    @Schema(description = "Message content")
    private String content;

    @Schema(description = "Send time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime sendTime;
}
