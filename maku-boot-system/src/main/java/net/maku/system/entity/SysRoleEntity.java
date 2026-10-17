package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;
import net.maku.system.enums.DataScopeEnum;

/**
 * Role
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
    /**
     * Role name
     */
    private String name;
    /**
     * Role code
     */
    private String roleCode;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Data scope  {@link DataScopeEnum}
     */
    private Integer dataScope;
    /**
     * Organization ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long orgId;
    /**
     * Tenant ID
     */
    private Long tenantId;
}
