package net.maku.iot.communication.mqtt.factory;

import lombok.RequiredArgsConstructor;
import net.maku.iot.communication.mqtt.handler.DevicePropertyChangeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Device running status change handler factory that automatically gets all implemented handler instances
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@RequiredArgsConstructor
public class DevicePropertyChangeHandlerFactory {
    private final ApplicationContext applicationContext;

    /**
     * All device running property change handlers
     */
    private List<DevicePropertyChangeHandler> handlers;

    /**
     * Get device running status change handlers
     *
     * @return
     */
    public List<DevicePropertyChangeHandler> getHandlers() {
        if (handlers != null) {
            return handlers;
        }
        handlers = Collections.unmodifiableList(
                new ArrayList<>(applicationContext.getBeansOfType(
                        DevicePropertyChangeHandler.class).values()));
        return handlers;
    }
}
