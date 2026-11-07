package net.maku.iot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.inbound.AbstractMqttMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;

/**
 * MqttUtility class
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@RequiredArgsConstructor
public class MqttUtils {
    private final AbstractMqttMessageDrivenChannelAdapter mqttMessageAdapter;

    /**
     * Dynamically subscribe to a topic, qos 1 by default
     *
     * @param topics
     */
    public void addTopic(String... topics) {
        mqttMessageAdapter.addTopic(topics);
    }

    /**
     * Dynamically subscribe to a topic
     *
     * @param topic
     * @param qos
     */
    public void addTopic(String topic, int qos) {
        mqttMessageAdapter.addTopic(topic, qos);
    }

    /**
     * Dynamically unsubscribe from a topic
     *
     * @param topics
     */
    public void removeTopic(String... topics) {
        mqttMessageAdapter.removeTopic(topics);
    }
}
