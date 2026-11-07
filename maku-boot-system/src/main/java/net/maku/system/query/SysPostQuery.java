package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Post management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Post management query")
public class SysPostQuery extends Query {
    @Schema(description = "Post code")
    private String postCode;

    @Schema(description = "Post name")
    private String postName;

    @Schema(description = "Status  0: disabled   1: normal")
    private Integer status;

}
