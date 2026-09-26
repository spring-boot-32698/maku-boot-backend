package net.maku.iot.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.iot.entity.IotDeviceEntity;
import net.maku.iot.communication.dto.DeviceCommandResponseDTO;
import net.maku.iot.query.IotDeviceQuery;
import net.maku.iot.communication.service.BaseCommunication;
import net.maku.iot.vo.DeviceCommandResponseAttributeDataVO;
import net.maku.iot.vo.DeviceCommandVO;
import net.maku.iot.vo.DeviceReportAttributeDataVO;
import net.maku.iot.vo.IotDeviceVO;

import java.util.List;

/**
 * Device table
 *
 * @author LSF maku_lsf@163.com
 */
public interface IotDeviceService extends BaseService<IotDeviceEntity> {

    PageResult<IotDeviceVO> page(IotDeviceQuery query);

    void save(IotDeviceVO vo);

    void update(IotDeviceVO vo);

    void delete(List<Long> idList);

    /**
     * Get the sending service by the protocol type of the device
     * @param device Device
     * @return
     */
    BaseCommunication getSendService(IotDeviceEntity device);

    /**
     * Get the sending service by protocol type
     * @param protocolType
     * @return
     */
    BaseCommunication getSendService(String protocolType);

    /**
     * Get the sending service by device ID
     * @param deviceId
     * @return
     */
    BaseCommunication getSendService(Long deviceId);

    /**
     * Send command to device - synchronous response mode
     *
     * @param vo
     */
    DeviceCommandResponseDTO syncSendCommand(DeviceCommandVO vo);

    /**
     * Send command to device - synchronous response mode - debug
     *
     * @param vo
     */
    DeviceCommandResponseDTO syncSendCommandDebug(DeviceCommandVO vo);

    /**
     * Send command to device - asynchronous response mode
     *
     * @param vo
     */
    void asyncSendCommand(DeviceCommandVO vo);

    /**
     * Simulate device property data report
     */
    void simulateDeviceReportAttributeData(DeviceReportAttributeDataVO vo);

    /**
     * Simulate device service command response data
     */
    void simulateDeviceCommandResponseAttributeData(DeviceCommandResponseAttributeDataVO vo);
}