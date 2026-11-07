package net.maku.iot.communication.mqtt.handler;


import net.maku.iot.communication.dto.DeviceCommandResponseDTO;

/**
 * Device command response handler
 *
 * @author LSF maku_lsf@163.com
 */
public interface DeviceCommandResponseHandler {
    /**
     * Device command response handling
     *
     * @param topic
     * @param commandResponse
     */
    void handle(String topic, DeviceCommandResponseDTO commandResponse);
}
