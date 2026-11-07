package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * SMS settings query
 *
 * @author Amu babamu@126.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "SMS settings query")
public class SysSmsConfigQuery extends Query {
    @Schema(description = "Platform type")
    private Integer platform;

    @Schema(description = "Group name")
    private String groupName;

}