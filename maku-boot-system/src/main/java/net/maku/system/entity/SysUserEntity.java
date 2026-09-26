package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;
import net.maku.system.enums.UserStatusEnum;

/**
 * User
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {
    /**
     * Username
     */
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * Name
     */
    private String realName;
    /**
     * Avatar
     */
    private String avatar;
    /**
     * Signature
     */
    private String signature;
    /**
     * Gender   0: male   1: female   2: unknown
     */
    private Integer gender;
    /**
     * Email
     */
    private String email;
    /**
     * Mobile number
     */
    private String mobile;
    /**
     * Organization ID
     */
    private Long orgId;
    /**
     * Direct supervisor ID
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long leaderId;
    /**
     * Super administrator   0: no   1: yes
     */
    private Integer superAdmin;
    /**
     * Status  {@link UserStatusEnum}
     */
    private Integer status;
    /**
     * Organization name
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * Tenant ID
     */
    private Long tenantId;
}
