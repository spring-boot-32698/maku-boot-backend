package net.maku.iot.communication.tcp.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.iot.communication.dto.TcpMsgDTO;
import net.maku.iot.communication.tcp.factory.TcpMessageHandlerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TCPServer connection handler
 *
 * @author LSF maku_lsf@163.com
 */
@Slf4j
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

    public static final AttributeKey<String> DEVICE_ID = AttributeKey.valueOf("DEVICE_ID");

    private ConcurrentMap<String, Channel> deviceChannels;
    private final TcpMessageHandlerFactory tcpMessageHandlerFactory;

    public ConnectionHandler(ConcurrentMap<String, Channel> deviceChannels,TcpMessageHandlerFactory tcpMessageHandlerFactory) {
        this.deviceChannels = deviceChannels;
        this.tcpMessageHandlerFactory = tcpMessageHandlerFactory;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.printf("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg == null) {
            return;
        }
        //Authentication
        if (authenticate(ctx, msg)) {
            //Custom message handling can be extended here according to business needs
            if (StrUtil.contains(msg.toString(), "topic")) {
                // Handle TCP messages
                handleTcpMessage( JsonUtils.parseObject(msg.toString(), TcpMsgDTO.class));
            } else {
                // Handle other types of messages
                log.warn("Received an unknown message type:{}", msg);
            }
        } else {
            ctx.close();
        }

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String deviceId = getDeviceId(ctx.channel());
        if (deviceId != null) {
            deviceChannels.remove(deviceId);
        }
        log.info(" Device {} Disconnect the connection", deviceId == null ? "Unknown device" : deviceId);
    }

    private void handleTcpMessage( TcpMsgDTO message) {
        String topic = message.getTopic();
        if (topic != null) {
            tcpMessageHandlerFactory.getHandlersForTopic(topic).forEach(handler -> {
                handler.handle(topic, message.getMsg());
            });
        } else {
            log.warn("Received a message whose topic is null.");
        }
    }


    /**
     * TCPConnection authentication, extend it according to business needs
     */
    private boolean authenticate(ChannelHandlerContext ctx, Object message) {
        String messageRegex = "\"(authenticate|deviceId)\"\\s*:\\s*\"\\d+\"";
        Pattern messagePattern = Pattern.compile(messageRegex);
        Matcher matcherPattern = messagePattern.matcher(message.toString());
        if (!matcherPattern.find()) {
            ctx.writeAndFlush("Device message not recognized!");
            return false;
        }
        if (StrUtil.contains(message.toString(), "authenticate")) {
            Pattern pattern = Pattern.compile("\"authenticate\"\\s*:\\s*\"(\\d+)\"");
            Matcher matcher = pattern.matcher(message.toString());
            if (matcher.find()) {
                String deviceId = matcher.group(1);
//                setDeviceId(ctx.channel(), deviceId);
                ctx.channel().attr(DEVICE_ID).set(deviceId);
                deviceChannels.put(deviceId, ctx.channel());
                ctx.writeAndFlush("authenticate passed");
            }
        }

        if (StrUtil.contains(message.toString(), "deviceId")) {
            Pattern pattern = Pattern.compile("\"deviceId\"\\s*:\\s*\"(\\d+)\"");
            Matcher matcher = pattern.matcher(message.toString());
            if (matcher.find()) {
                String deviceId = matcher.group(1);
                Channel channel = deviceChannels.get(deviceId);
                if (channel == null) {
                    ctx.writeAndFlush("Device connection does not exist! Please reconnect");
                    return false;
                }
            }
        }
        return true;
    }

    private String getDeviceId(Channel channel) {
        return channel.attr(DEVICE_ID).get();
    }

//    private String setDeviceId(Channel channel, String deviceId) {
//        return channel.attr(AttributeKey.<String>valueOf("deviceId")).setIfAbsent(deviceId);
//    }
}
