package net.maku.iot.communication.service;

import net.maku.iot.entity.IotDeviceEntity;
import net.maku.iot.enums.DeviceCommandEnum;
import net.maku.iot.communication.dto.DeviceCommandResponseDTO;


/**
 * Capabilities of the communication protocol
 *
 * @author LSF maku_lsf@163.com
 */
public interface BaseCommunication {

    // Send command asynchronously,Do not wait for device response
    String asyncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload);

    //Send synchronously, wait for device response
    DeviceCommandResponseDTO syncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload);

    //Send synchronously, wait for device response, debug implementation
    DeviceCommandResponseDTO syncSendCommandDebug(IotDeviceEntity device, DeviceCommandEnum command, String payload);

    //Simulate device property report
    void simulateDeviceReportAttributeData(IotDeviceEntity device, String payload);

    //Simulate device service command response data
    void simulateDeviceCommandResponseAttributeData(IotDeviceEntity device, String payload);


}
