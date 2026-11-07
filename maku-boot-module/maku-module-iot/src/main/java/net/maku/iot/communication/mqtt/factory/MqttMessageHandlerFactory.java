package net.maku.iot.communication.mqtt.factory;

import lombok.RequiredArgsConstructor;
import net.maku.iot.communication.mqtt.handler.MqttMessageHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MQTTMessage handler factory that automatically gets all implemented handler instances
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@RequiredArgsConstructor
public class MqttMessageHandlerFactory {
    private final ApplicationContext applicationContext;

    /**
     * All message handlers
     */
    private List<MqttMessageHandler> messageHandlers;

    private List<MqttMessageHandler> loadHandlers() {
        if (messageHandlers != null) {
            return messageHandlers;
        }
        messageHandlers = new ArrayList<>(applicationContext.getBeansOfType(MqttMessageHandler.class).values());
        return messageHandlers;
    }

    /**
     * Get the handler corresponding to the topic
     *
     * @param topic Topic
     * @return Handler list
     */
    public List<MqttMessageHandler> getHandlersForTopic(String topic) {
        return Collections.unmodifiableList(loadHandlers().stream()
                .filter(handler -> handler.supports(topic))
                .collect(Collectors.toList()));
    }
}
