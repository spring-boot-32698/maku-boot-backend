package net.maku.member.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
 * Member management query
 *
 * @author Amu babamu@126.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Member management query")
public class MemberUserQuery extends Query {
    @Schema(description = "Nickname")
    private String nickName;

    @Schema(description = "Mobile number")
    private String mobile;

    @Schema(description = "Gender")
    private Integer gender;

    @Schema(description = "Status")
    private Integer status;

}