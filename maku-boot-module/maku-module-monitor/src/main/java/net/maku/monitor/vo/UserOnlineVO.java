package net.maku.monitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Online users
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Online users")
public class UserOnlineVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Name")
    private String realName;

    @Schema(description = "Gender")
    private Integer gender;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "accessToken")
    private String accessToken;
}
