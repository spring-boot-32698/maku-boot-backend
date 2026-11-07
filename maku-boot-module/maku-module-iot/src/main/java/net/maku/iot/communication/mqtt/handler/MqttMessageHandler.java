package net.maku.iot.communication.mqtt.handler;

/**
 * MQTTSubscribed message handling interface
 *
 * @author LSF maku_lsf@163.com
 */
public interface MqttMessageHandler {
    /**
     * Whether handling the specified topic is supported
     *
     * @param topic
     * @return
     */
    boolean supports(String topic);

    /**
     * mqttMessage handling interface
     *
     * @param topic
     * @param message
     */
    void handle(String topic, String message);
}