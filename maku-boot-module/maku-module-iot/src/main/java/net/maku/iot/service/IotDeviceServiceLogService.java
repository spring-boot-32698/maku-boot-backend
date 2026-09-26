package net.maku.iot.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.iot.entity.IotDeviceServiceLogEntity;
import net.maku.iot.enums.DeviceCommandEnum;
import net.maku.iot.query.IotDeviceServiceLogQuery;
import net.maku.iot.vo.IotDeviceServiceLogVO;

import java.util.List;

/**
 * Device service log
 *
 * @author LSF maku_lsf@163.com
 */
public interface IotDeviceServiceLogService extends BaseService<IotDeviceServiceLogEntity> {

    PageResult<IotDeviceServiceLogVO> page(IotDeviceServiceLogQuery query);

    void save(IotDeviceServiceLogVO vo);

    void update(IotDeviceServiceLogVO vo);

    void delete(List<Long> idList);

    /**
     * Create device service log
     *
     * @param deviceId Device ID
     * @param tenantId Tenant ID
     * @param command  Service type
     * @param eventUid Event UID
     * @param payload  Event data
     * @return Device event
     */
    IotDeviceServiceLogEntity createDeviceServiceLog(Long deviceId, Long tenantId, DeviceCommandEnum command,
                                                     String eventUid, Object payload);

    /**
     * Create and save device service log
     *
     * @param deviceId Device ID
     * @param tenantId Tenant ID
     * @param command  Service type
     * @param eventUid Event UID
     * @param payload  Event data
     * @return Device event
     */
    void createAndSaveDeviceServiceLog(Long deviceId, Long tenantId, DeviceCommandEnum command,
                                       String eventUid, Object payload);
}