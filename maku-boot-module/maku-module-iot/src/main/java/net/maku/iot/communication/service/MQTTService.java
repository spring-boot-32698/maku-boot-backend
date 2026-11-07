package net.maku.iot.communication.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.exception.ServerException;
import net.maku.iot.communication.dto.CommandResponseChan;
import net.maku.iot.communication.dto.DeviceCommandDTO;
import net.maku.iot.communication.dto.DeviceCommandResponseDTO;
import net.maku.iot.communication.mqtt.MqttGateway;
import net.maku.iot.dto.DeviceClientDTO;
import net.maku.iot.entity.IotDeviceEntity;
import net.maku.iot.enums.DeviceCommandEnum;
import net.maku.iot.enums.DeviceTopicEnum;
import net.maku.iot.service.IotDeviceServiceLogService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * MQTTProtocol service class
 *
 * @author LSF maku_lsf@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MQTTService implements BaseCommunication {

    private final MqttGateway mqttGateway;
    private final IotDeviceServiceLogService iotDeviceEventLogService;

    /**
     * Send command asynchronously, return the command id
     *
     * @param device
     * @param command
     * @param payload
     * @return
     */
    @Override
    public String asyncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload) {
        return asyncSendCommand(device, command, payload, Boolean.FALSE);
    }

    /**
     * Send command asynchronously, return the command id
     *
     * @param device
     * @param command
     * @param payload
     * @param retained
     * @return
     */
    public String asyncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload, boolean retained) {
        // Build command object
        String commandId = StrUtil.replaceChars(UUID.randomUUID().toString(), "-", "");
        DeviceCommandDTO commandDTO = new DeviceCommandDTO();
        commandDTO.setCommand(command);
        commandDTO.setId(commandId);
        commandDTO.setPayload(payload);
        String commandTopic = DeviceTopicEnum.COMMAND.buildTopic(DeviceClientDTO.from(device));

        // Send command to device command topic
        try {
            mqttGateway.sendToMqtt(commandTopic, retained, JSONUtil.toJsonStr(commandDTO));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServerException(StrUtil.format("Send'{}'Command:{} To device:{}-{}, Topic:{} Failed",
                    command.getTitle(), commandId, device.getCode(), device.getName(), commandTopic));
        }
        log.info("Send'{}'Command:{} To device:{}-{}, Topic:{} Success", command.getTitle(), commandId, device.getCode(), device.getName(), commandTopic);
        iotDeviceEventLogService.createAndSaveDeviceServiceLog(device.getId(), device.getTenantId(), command, commandId, payload);
        return commandId;
    }

    /**
     * Send command synchronously and return the response result
     *
     * @param device
     * @param command
     * @param payload
     * @return
     */
    public DeviceCommandResponseDTO syncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload) {
        return syncSendCommand(device, command, payload, Boolean.FALSE);
    }

    /**
     * Send command and return the response result
     *
     * @param device
     * @param command
     * @param payload
     * @param retained
     * @return
     */
    public DeviceCommandResponseDTO syncSendCommand(IotDeviceEntity device, DeviceCommandEnum command, String payload, boolean retained) {
        // Build and send command
        String commandId = asyncSendCommand(device, command, payload, retained);
        // Wait for the result
        Object receiver = CommandResponseChan.getInstance(commandId, true).get(commandId);
        if (receiver == null) {
            throw new ServerException(StrUtil.format("{}Device did not reply", device.getName()));
        }
        return (DeviceCommandResponseDTO) receiver;
    }

    /**
     * Send command and return the response result, simulating the device response
     *
     * @param device
     * @param command
     * @param payload
     * @return
     */
    public DeviceCommandResponseDTO syncSendCommandDebug(IotDeviceEntity device, DeviceCommandEnum command, String payload) {
        // Build and send command
        String commandId = asyncSendCommand(device, command, payload);

        // 2 seconds later simulate the device response
        new Thread(() -> {
            try {
                //Simulate normal device response
                Thread.sleep(2000);
                //Simulate device timeout response
                //Thread.sleep(15000);
                DeviceCommandResponseDTO simulateResponseDto = new DeviceCommandResponseDTO();
                simulateResponseDto.setCommandId(commandId);
                simulateResponseDto.setResponsePayload(command.getTitle() + ",Device executed successfully!");
                simulateResponseDto.setCommand(command);
                simulateDeviceCommandResponseAttributeData(device, JSONUtil.toJsonStr(simulateResponseDto));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Simulated device response thread was interrupted", e);
            }
        }).start();

        // Wait for the result
        Object receiver = CommandResponseChan.getInstance(commandId, true).get(commandId);
        if (receiver == null) {
            throw new ServerException(StrUtil.format("{}Device did not reply", device.getName()));
        }
        return (DeviceCommandResponseDTO) receiver;
    }

    /**
     * Simulate device property report
     */
    @Override
    public void simulateDeviceReportAttributeData(IotDeviceEntity device, String payload) {
        // Wrap the topic of device property reports
        String commandTopic = DeviceTopicEnum.PROPERTY.buildTopic(DeviceClientDTO.from(device));
        try {
            mqttGateway.sendToMqtt(commandTopic, payload);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServerException(StrUtil.format("Simulated device:{}-{},Failed to simulate property report! Topic:{} ",
                    device.getCode(), device.getName(), commandTopic));
        }
    }


    /**
     * Simulate device service command response data
     *
     * @param device
     * @param payload
     */
    @Override
    public void simulateDeviceCommandResponseAttributeData(IotDeviceEntity device, String payload) {
        // Wrap the topic of device command execution results
        String commandTopic = DeviceTopicEnum.COMMAND_RESPONSE.buildTopic(DeviceClientDTO.from(device));
        try {
            mqttGateway.sendToMqtt(commandTopic, payload);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServerException(StrUtil.format("Simulated device:{}-{},Failed to simulate reporting the command execution result! Topic:{} ",
                    device.getCode(), device.getName(), commandTopic));
        }
    }

    /**
     * Device command response handling, puts the device response result into the channel
     *
     * @param commandResponse Device command response
     */
    public void commandReplied(DeviceCommandResponseDTO commandResponse) {
        CommandResponseChan commandResponseChan = CommandResponseChan.getInstance(commandResponse.getCommandId(), false);
        commandResponseChan.put(commandResponse);
    }


}
