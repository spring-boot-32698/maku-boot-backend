package net.maku.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Member management
 *
 * @author Amu babamu@126.com
 */

@Data
@TableName("member_user")
public class MemberUserEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * Nickname
     */
    private String nickName;

    /**
     * Mobile number
     */
    private String mobile;

    /**
     * Avatar
     */
    private String avatar;

    /**
     * Date of birth
     */
    private LocalDateTime birthday;

    /**
     * Gender
     */
    private Integer gender;

    /**
     * Third-party platform, unique identifier
     */
    private String openid;

    /**
     * Last login IP
     */
    private String lastLoginIp;

    /**
     * Last login time
     */
    private LocalDateTime lastLoginTime;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Status
     */
    private Integer status;

    /**
     * Tenant ID
     */
    private Long tenantId;

    /**
     * Version number
     */
    private Integer version;

    /**
     * Delete flag
     */
    private Integer deleted;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

}