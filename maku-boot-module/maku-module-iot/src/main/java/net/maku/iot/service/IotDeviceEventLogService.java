package net.maku.iot.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.iot.entity.IotDeviceEventLogEntity;
import net.maku.iot.enums.DeviceEventTypeEnum;
import net.maku.iot.query.IotDeviceEventLogQuery;
import net.maku.iot.vo.IotDeviceEventLogVO;

import java.util.List;

/**
 * Device event log
 *
 * @author LSF maku_lsf@163.com
 */
public interface IotDeviceEventLogService extends BaseService<IotDeviceEventLogEntity> {

    PageResult<IotDeviceEventLogVO> page(IotDeviceEventLogQuery query);

    void save(IotDeviceEventLogVO vo);

    void update(IotDeviceEventLogVO vo);

    void delete(List<Long> idList);

    /**
     * Create device event
     *
     * @param deviceId  Device ID
     * @param tenantId  Tenant ID
     * @param eventType Event type
     * @param eventUid  Event UID
     * @param payload   Event data
     * @return Device event
     */
    IotDeviceEventLogEntity createDeviceEvent(Long deviceId, Long tenantId, DeviceEventTypeEnum eventType,
                                              String eventUid, Object payload);

    /**
     * Create and save device event
     *
     * @param deviceId  Device ID
     * @param tenantId  Tenant ID
     * @param eventType Event type
     * @param eventUid  Event UID
     * @param payload   Event data
     * @return Device event
     */
    void createAndSaveDeviceEvent(Long deviceId, Long tenantId, DeviceEventTypeEnum eventType,
                                  String eventUid, Object payload);
}