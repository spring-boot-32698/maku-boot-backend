package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * SMS log query
 *
 * @author Amu babamu@126.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "SMS log query")
public class SysSmsLogQuery extends Query {
    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Platform type")
    private Integer platform;

}