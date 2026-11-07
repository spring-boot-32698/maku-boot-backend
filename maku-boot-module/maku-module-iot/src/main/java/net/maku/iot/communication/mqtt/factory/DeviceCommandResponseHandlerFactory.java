package net.maku.iot.communication.mqtt.factory;

import lombok.RequiredArgsConstructor;
import net.maku.iot.communication.mqtt.handler.DeviceCommandResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Device command response handler factory that automatically gets all implemented handler instances
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@RequiredArgsConstructor
public class DeviceCommandResponseHandlerFactory {
    private final ApplicationContext applicationContext;

    /**
     * All device command response handlers
     */
    private List<DeviceCommandResponseHandler> handlers;

    /**
     * Get device command response handlers
     *
     * @return
     */
    public List<DeviceCommandResponseHandler> getHandlers() {
        if (handlers != null) {
            return handlers;
        }
        handlers = Collections.unmodifiableList(
                new ArrayList<>(applicationContext.getBeansOfType(
                        DeviceCommandResponseHandler.class).values()));
        return handlers;
    }
}
