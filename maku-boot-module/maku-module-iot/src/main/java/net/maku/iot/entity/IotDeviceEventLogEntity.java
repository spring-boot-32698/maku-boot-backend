package net.maku.iot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.mybatis.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Device event log
 *
 * @author LSF maku_lsf@163.com
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("iot_device_event_log")
public class IotDeviceEventLogEntity extends BaseEntity {

    /**
     * Device id
     */
    private Long deviceId;

    /**
     * Event type
     */
    private Integer eventType;

    /**
     * Event identifier id
     */
    private String eventUid;

    /**
     * Event data
     */
    private String eventPayload;

    /**
     * Event time
     */
    private LocalDateTime eventTime;

    /**
     * Tenant ID
     */
    private Long tenantId;


}