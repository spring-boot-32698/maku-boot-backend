package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Post management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_post")
public class SysPostEntity extends BaseEntity {
    /**
     * Post code
     */
    private String postCode;
    /**
     * Post name
     */
    private String postName;
    /**
     * Sort
     */
    private Integer sort;
    /**
     * Status  0: disabled   1: normal
     */
    private Integer status;
    /**
     * Tenant ID
     */
    private Long tenantId;
}