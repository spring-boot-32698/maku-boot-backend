package net.maku.iot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Device table
 *
 * @author LSF maku_lsf@163.com
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("iot_device")
public class IotDeviceEntity extends BaseEntity {

    /**
     * Code
     */
    private String code;

    /**
     * Name
     */
    private String name;

    /**
     * Device type, 1. handheld device, 2. cabinet, 3. sensor device
     */
    private Integer type;

    /**
     * Communication protocol type between device and server
     */
    private String protocolType;

    /**
     * Unique identifier
     */
    private String uid;

    /**
     * Device key
     */
    private String secret;

    /**
     * AppVersion number
     */
    private String appVersion;

    /**
     * Battery level percentage
     */
    private String batteryPercent;

    /**
     * Temperature
     */
    private String temperature;

    /**
     * Status, 0 disabled, 1 normal
     */
    private Integer status;

    /**
     * Running status, 0. offline 1. online 2. normal standby 3. in use 4. OTA upgrading
     */
    private Integer runningStatus;

    /**
     * Online time
     */
    private LocalDateTime upTime;

    /**
     * Offline time
     */
    private LocalDateTime downTime;

    /**
     * Tenant ID
     */
    private Long tenantId;


}