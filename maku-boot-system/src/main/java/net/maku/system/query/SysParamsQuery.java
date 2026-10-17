package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Parameter management query
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Parameter management query")
public class SysParamsQuery extends Query {
    @Schema(description = "System parameters")
    private Integer paramType;

    @Schema(description = "Parameter key")
    private String paramKey;

    @Schema(description = "Parameter value")
    private String paramValue;

}