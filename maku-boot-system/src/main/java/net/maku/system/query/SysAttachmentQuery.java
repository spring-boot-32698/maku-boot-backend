package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Attachment management query
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Attachment management query")
public class SysAttachmentQuery extends Query {
    @Schema(description = "Attachment name")
    private String name;

    @Schema(description = "Storage platform")
    private String platform;

}