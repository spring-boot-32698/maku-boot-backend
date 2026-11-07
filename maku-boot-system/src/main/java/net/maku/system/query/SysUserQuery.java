package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

import java.util.List;

/**
 * User query
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "User query")
public class SysUserQuery extends Query {
    @Schema(description = "Username")
    private String username;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Gender")
    private Integer gender;

    @Schema(description = "Organization ID")
    private List<Long> orgId;

}
