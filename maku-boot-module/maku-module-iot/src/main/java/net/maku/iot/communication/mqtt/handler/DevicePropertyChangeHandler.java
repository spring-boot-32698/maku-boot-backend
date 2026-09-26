package net.maku.iot.communication.mqtt.handler;


import net.maku.iot.communication.dto.DevicePropertyDTO;

/**
 * Device property change handler
 *
 * @author LSF maku_lsf@163.com
 */
public interface DevicePropertyChangeHandler {
    /**
     * Device property status change handling
     *
     * @param topic
     * @param deviceStatus
     */
    void handle(String topic, DevicePropertyDTO deviceStatus);
}
