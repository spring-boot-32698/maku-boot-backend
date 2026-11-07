package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Operation log query
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Operation log query")
public class SysLogOperateQuery extends Query {
    @Schema(description = "User")
    private String realName;

    @Schema(description = "Module name")
    private String module;

    @Schema(description = "Request URI")
    private String reqUri;

    @Schema(description = "Operation status")
    private Integer status;

}