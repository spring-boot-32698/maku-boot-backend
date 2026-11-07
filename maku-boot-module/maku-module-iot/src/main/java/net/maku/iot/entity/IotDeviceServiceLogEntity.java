package net.maku.iot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Device service log
 *
 * @author LSF maku_lsf@163.com
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("iot_device_service_log")
public class IotDeviceServiceLogEntity extends BaseEntity {

    /**
     * Device id
     */
    private Long deviceId;

    /**
     * Service type
     */
    private Integer serviceType;

    /**
     * Service identifier id
     */
    private String serviceUid;

    /**
     * Service data
     */
    private String servicePayload;

    /**
     * Service time
     */
    private LocalDateTime serviceTime;

    /**
     * Tenant ID
     */
    private Long tenantId;


}