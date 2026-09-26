package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Menu management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {
    /**
     * Parent ID
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long pid;
    /**
     * Menu name
     */
    private String name;
    /**
     * Menu URL
     */
    private String url;
    /**
     * Authorization identifier(Separate multiple values with commas, e.g.: sys:menu:list,sys:menu:save)
     */
    private String authority;
    /**
     * Type   0: menu   1: button   2: API
     */
    private Integer type;
    /**
     * Open mode   0: internal   1: external
     */
    private Integer openStyle;
    /**
     * Menu icon
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String icon;
    /**
     * Sort
     */
    private Integer sort;

}