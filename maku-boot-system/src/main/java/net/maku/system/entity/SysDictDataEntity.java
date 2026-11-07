package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Data dictionary
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_data")
public class SysDictDataEntity extends BaseEntity {
    /**
     * Dictionary type ID
     */
    private Long dictTypeId;
    /**
     * Dictionary label
     */
    private String dictLabel;
    /**
     * Dictionary value
     */
    private String dictValue;
    /**
     * Label style
     */
    private String labelClass;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Sort
     */
    private Integer sort;
    /**
     * Tenant ID
     */
    private Long tenantId;
}
