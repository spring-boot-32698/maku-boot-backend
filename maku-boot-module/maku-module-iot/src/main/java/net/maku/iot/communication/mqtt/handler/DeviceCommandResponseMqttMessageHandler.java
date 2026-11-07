package net.maku.iot.communication.mqtt.handler;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.iot.communication.service.MQTTService;
import net.maku.iot.communication.dto.DeviceCommandResponseDTO;
import net.maku.iot.communication.mqtt.factory.DeviceCommandResponseHandlerFactory;
import net.maku.iot.enums.DeviceTopicEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Device command response handler
 *
 * @author LSF maku_lsf@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceCommandResponseMqttMessageHandler implements MqttMessageHandler {

    private final DeviceCommandResponseHandlerFactory deviceCommandResponseHandlerFactory;

    private final MQTTService deviceMqttService;

    @Override
    public boolean supports(String topic) {
        return DeviceTopicEnum.startsWith(topic, DeviceTopicEnum.COMMAND_RESPONSE.getTopic());
    }

    @Override
    public void handle(String topic, String message) {
        DeviceCommandResponseDTO commandResponseDTO = parseCommandReplyMessage(topic, message);
        Optional.ofNullable(commandResponseDTO.getCommand())
                .orElseThrow(() -> new IllegalArgumentException(StrUtil.format("Missing command type! Topic:'{}',Message:{}", topic, message)));
        Optional.ofNullable(commandResponseDTO.getCommandId())
                .orElseThrow(() -> new IllegalArgumentException(StrUtil.format("Missing command ID! Topic:'{}',Message:{}", topic, message)));

        Optional.ofNullable(commandResponseDTO)
                .ifPresent(responseDTO -> {
                    // Call the command response handling logic of the device command executor
                    try {
                        deviceMqttService.commandReplied( responseDTO);
                    } catch (Exception e) {
                        log.error(StrUtil.format("Error calling the device command executor response handling method, topic:{}, message:{}", topic, message), e);
                    }
                    // Call the custom command response handler
                    try {
                        deviceCommandResponseHandlerFactory.getHandlers().forEach(h -> h.handle(topic, responseDTO));
                    } catch (Exception e) {
                        log.error(StrUtil.format("Error calling the device command response handler, topic:{}, message:{}", topic, message), e);
                    }
                });
    }

    private DeviceCommandResponseDTO parseCommandReplyMessage(String topic, String message) {
        try {
            DeviceCommandResponseDTO commandResponse = JsonUtils.parseObject(message, DeviceCommandResponseDTO.class);
            if (StrUtil.isBlank(commandResponse.getCommandId())) {
                log.error(StrUtil.format("Topic'{}'message of,Missing command ID", topic));
                return null;
            }
            return commandResponse;

        } catch (Exception e) {
            log.error(StrUtil.format("Set the topic'{}'message failed to parse into a device command response object", topic), e);
            return null;
        }
    }
}