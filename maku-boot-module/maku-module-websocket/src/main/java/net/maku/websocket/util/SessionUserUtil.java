package net.maku.websocket.util;

import net.maku.framework.security.user.UserDetail;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public class SessionUserUtil {
    private static final String SESSION_USER = "SESSION_USER";

    /**
     * Set user
     *
     * @param user       User
     * @param attributes attributes
     */
    public static void setUser(UserDetail user, Map<String, Object> attributes) {
        attributes.put(SESSION_USER, user);
    }

    /**
     * Get user
     */
    public static UserDetail getUser(WebSocketSession session) {
        return (UserDetail) session.getAttributes().get(SESSION_USER);
    }

}
