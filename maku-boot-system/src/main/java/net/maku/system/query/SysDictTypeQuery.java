package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Dictionary type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Dictionary type query")
public class SysDictTypeQuery extends Query {
    @Schema(description = "Dictionary type")
    private String dictType;

    @Schema(description = "Dictionary name")
    private String dictName;

}
