package net.maku.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SMS settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "SMS settings")
public class SysSmsConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Platform type")
    private Integer platform;

    @Schema(description = "Group name; a group can be specified when sending SMS")
    private String groupName;

    @Schema(description = "SMS signature")
    private String signName;

    @Schema(description = "SMS template")
    private String templateId;

    @Schema(description = "ID of the SMS application, e.g.: Tencent Cloud")
    private String appId;

    @Schema(description = "Required for Tencent Cloud international SMS, Huawei Cloud, etc.")
    private String senderId;

    @Schema(description = "Access address, e.g.: Huawei Cloud")
    private String url;

    @Schema(description = "AccessKey")
    private String accessKey;

    @Schema(description = "SecretKey")
    private String secretKey;

    @Schema(description = "Status  0: disabled   1: enabled")
    private Integer status;

    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}