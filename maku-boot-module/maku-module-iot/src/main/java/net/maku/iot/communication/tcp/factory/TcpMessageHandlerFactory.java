package net.maku.iot.communication.tcp.factory;

import lombok.RequiredArgsConstructor;
import net.maku.iot.communication.tcp.handler.TCPMessageHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TCPMessage handler factory that automatically gets all implemented handler instances
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@RequiredArgsConstructor
public class TcpMessageHandlerFactory {
    private final ApplicationContext applicationContext;

    /**
     * All message handlers
     */
    private List<TCPMessageHandler> messageHandlers;

    private List<TCPMessageHandler> loadHandlers() {
        if (messageHandlers != null) {
            return messageHandlers;
        }
        messageHandlers = new ArrayList<>(applicationContext.getBeansOfType(TCPMessageHandler.class).values());
        return messageHandlers;
    }

    /**
     * Get the tcp message handler corresponding to the topic
     *
     * @param topic Topic
     * @return Handler list
     */
    public List<TCPMessageHandler> getHandlersForTopic(String topic) {
        return Collections.unmodifiableList(loadHandlers().stream()
                .filter(handler -> handler.supports(topic))
                .collect(Collectors.toList()));
    }
}
