package net.maku.websocket.message;

import lombok.Data;

/**
 * websocket json Message
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class JsonDataMessage<T> {
    /**
     * Message type, reserved
     */
    private String type = "default";
    /**
     * Message data
     */
    private T data;

}
