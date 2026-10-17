package net.maku.websocket.sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.websocket.message.JsonDataMessage;
import net.maku.websocket.session.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * WebSocket Message sending
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
@Component
@AllArgsConstructor
public class WebSocketMessageSender {
    private final WebSocketSessionManager webSocketSessionManager;

    /**
     * Send message
     *
     * @param userId  User ID
     * @param message Message content
     */
    public void send(Long userId, JsonDataMessage<?> message) {
        webSocketSessionManager.getSessionList(userId).forEach(session -> send(session, message));
    }

    /**
     * Send message
     *
     * @param userIdList User ID list
     * @param message    Message content
     */
    public void send(List<Long> userIdList, JsonDataMessage<?> message) {
        userIdList.forEach(userId -> send(userId, message));
    }

    /**
     * Send message
     *
     * @param session WebSocketSession
     * @param message Message content
     */
    public void send(WebSocketSession session, JsonDataMessage<?> message) {
        try {
            session.sendMessage(new TextMessage(JsonUtils.toJsonString(message)));
        } catch (Exception e) {
            log.error("send websocket message error,{}", e.getMessage(), e);
        }
    }
}
