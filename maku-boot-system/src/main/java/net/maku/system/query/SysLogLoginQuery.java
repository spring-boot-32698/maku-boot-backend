package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Login log query
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Login log query")
public class SysLogLoginQuery extends Query {
    @Schema(description = "Username")
    private String username;

    @Schema(description = "Login location")
    private String address;

    @Schema(description = "Login status  0: failed   1: succeeded")
    private Integer status;

}