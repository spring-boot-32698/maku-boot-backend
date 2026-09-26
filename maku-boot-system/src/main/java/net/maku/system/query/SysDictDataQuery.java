package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Dictionary data
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Dictionary data query")
public class SysDictDataQuery extends Query {
    @Schema(description = "Dictionary type ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Dictionary type ID cannot be empty")
    private Long dictTypeId;

}
