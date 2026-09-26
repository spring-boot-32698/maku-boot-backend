package net.maku.iot.communication.tcp;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.iot.communication.dto.DeviceCommandDTO;
import net.maku.iot.communication.dto.TcpMsgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * TCP Gateway
 *
 * @author LSF maku_lsf@163.com
 */
@Component
@Slf4j
public class TcpGateway {

    @Autowired
    private final ConcurrentMap<String, Channel> deviceChannels;

    @Autowired
    public TcpGateway(ConcurrentMap<String, Channel> deviceChannels) {
        this.deviceChannels = deviceChannels;
    }

    /**
     * Send command to device
     * @param deviceId Device ID
     * @param commandTopic Command topic
     * @param payload Command content
     */
    public void sendCommandToDevice(Long deviceId, String commandTopic, String payload) {
        Channel channel = deviceChannels.get(deviceId.toString());
        if (channel != null && channel.isActive()) {
            TcpMsgDTO tcpMsgDTO = new TcpMsgDTO();
            tcpMsgDTO.setTopic(commandTopic);
            DeviceCommandDTO deviceCommandDTO = JsonUtils.parseObject(payload, DeviceCommandDTO.class);
            deviceCommandDTO.setDeviceId(deviceId.toString());
            tcpMsgDTO.setMsg(deviceCommandDTO);

            String commandJson = JsonUtils.toJsonString(tcpMsgDTO);
//            channel.writeAndFlush(commandJson);
            log.info("Send command to device {}: {}", deviceId, payload);
        } else {
            throw new ServerException("Device"+deviceId+"Not online or channel invalid");
        }
    }

    public void simulateDeviceReport(Long deviceId, String commandTopic, String payload, Class reportDtoclazz) {
        Channel channel = deviceChannels.get(deviceId.toString());
        if (channel != null && channel.isActive()) {
            try {
                TcpMsgDTO tcpMsgDTO = new TcpMsgDTO();
                tcpMsgDTO.setTopic(commandTopic);
                tcpMsgDTO.setMsg(JsonUtils.parseObject(payload, reportDtoclazz));
                String devicePropertyJson = JsonUtils.toJsonString(tcpMsgDTO);
                // Simulate a report and trigger channelRead handling
                channel.pipeline().fireChannelRead(devicePropertyJson);
                log.info("Simulated device {} Report data: {}", deviceId, devicePropertyJson);
            } catch (Exception e) {
                log.error("Error occurred while the simulated device reported data", e);
            }
        } else {
            throw new ServerException("Device " + deviceId + " Not online or channel invalid");
        }
    }


}
