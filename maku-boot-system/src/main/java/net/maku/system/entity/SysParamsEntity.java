package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

/**
 * Parameter management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_params")
public class SysParamsEntity extends BaseEntity {

    /**
     * Parameter name
     */
    private String paramName;

    /**
     * System parameters
     */
    private Integer paramType;

    /**
     * Parameter key
     */
    private String paramKey;

    /**
     * Parameter value
     */
    private String paramValue;

    /**
     * Remarks
     */
    private String remark;

    /**
     * Tenant ID
     */
    private Long tenantId;
}