package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Mail log query
 *
 * @author Amu babamu@126.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Mail log query")
public class SysMailLogQuery extends Query {
    @Schema(description = "Sender email")
    private String mailFrom;

    @Schema(description = "Platform type")
    private Integer platform;
}