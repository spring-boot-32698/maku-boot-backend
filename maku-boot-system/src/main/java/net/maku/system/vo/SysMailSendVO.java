package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * Mail sending
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "Mail sending")
public class SysMailSendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Platform")
    private Integer platform;

    @Schema(description = "Mail format")
    private String mailFormat;

    @Schema(description = "Sender email")
    private String mailFrom;

    @Schema(description = "Sender nickname")
    private String formAlias;

    @Schema(description = "Recipient email")
    private String mailTos;

    @Schema(description = "Recipient list")
    private String receiversName;

    @Schema(description = "Template name")
    private String templateName;

    @Schema(description = "Label name")
    private String tagName;

    @Schema(description = "Mail subject")
    private String subject;

    @Schema(description = "Mail body")
    private String content;

}