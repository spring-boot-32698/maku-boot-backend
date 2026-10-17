package net.maku.framework.common.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Query public parameters
 *
 * @author Amu babamu@126.com
 *         <a href="https://maku.net">MAKU</a>
 */
@Data
public class Query {
    @NotNull(message = "Page number cannot be empty")
    @Min(value = 1, message = "Minimum page number is 1")
    @Schema(description = "Current page number", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer page;

    @NotNull(message = "Items per page cannot be empty")
    @Range(min = 1, max = 1000, message = "Items per page, range 1-1000")
    @Schema(description = "Items per page", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer limit;

    @Schema(description = "Sort field")
    String order;

    @Schema(description = "Whether ascending")
    boolean asc;
}
