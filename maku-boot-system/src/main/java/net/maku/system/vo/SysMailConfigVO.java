package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Mail settings
 *
 * @author Amu babamu@126.com
 */
@Data
@Schema(description = "Mail settings")
public class SysMailConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Platform type")
    private Integer platform;

    @Schema(description = "Group name; a group can be specified when sending mail")
    private String groupName;

    @Schema(description = "SMTPServer")
    private String mailHost;

    @Schema(description = "SMTPPort")
    private Integer mailPort;

    @Schema(description = "Sender email")
    private String mailFrom;

    @Schema(description = "Sender password")
    private String mailPass;

    @Schema(description = "regionId")
    private String regionId;

    @Schema(description = "Alibaba Cloud endpoint")
    private String endpoint;

    @Schema(description = "AccessKey")
    private String accessKey;

    @Schema(description = "SecretKey")
    private String secretKey;

    @Schema(description = "Status  0: disabled   1: enabled")
    private Integer status;

    @Schema(description = "Version number")
    private Integer version;

    @Schema(description = "Delete flag")
    private Integer deleted;

    @Schema(description = "Creator")
    private Long creator;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;

    @Schema(description = "Updater")
    private Long updater;

    @Schema(description = "Update time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date updateTime;


}