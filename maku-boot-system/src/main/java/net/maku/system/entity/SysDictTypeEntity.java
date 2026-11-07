package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Dictionary type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_type")
public class SysDictTypeEntity extends BaseEntity {
    /**
     * Dictionary type
     */
    private String dictType;
    /**
     * Dictionary name
     */
    private String dictName;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Sort
     */
    private Integer sort;
    /**
     * Source  0: dictionary data  1: dynamic SQL
     */
    private Integer dictSource;
    /**
     * Dynamic sql
     */
    private String dictSql;
    /**
     * Parent node
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long pid;
    /**
     * Whether there are child nodes  0: none  1: yes
     */
    private Integer hasChild;
    /**
     * Tenant ID
     */
    private Long tenantId;
}