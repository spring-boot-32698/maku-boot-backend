package net.maku.iot.communication.mqtt.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.maku.iot.communication.mqtt.factory.MqttMessageHandlerFactory;
import net.maku.iot.enums.DeviceTopicEnum;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MQTT Configuration class for setting up and managing MQTT connections and message handling.
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Slf4j
@Configuration
@IntegrationComponentScan
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttConfig {
    public static final String OUTBOUND_CHANNEL = "mqttOutboundChannel";
    public static final String INPUT_CHANNEL = "mqttInputChannel";

    // MQTT Username
    private String username;

    // MQTT Password
    private String password;

    // MQTT Server URL
    private String host;

    // Client ID
    private String clientId;

    // Default theme
    private String defaultTopic;

    // Factory for handling MQTT messages
    @Resource
    private MqttMessageHandlerFactory mqttMessageHandlerFactory;

    @PostConstruct
    public void init() {
        log.info("MQTT Host: {} Client ID: {} Default theme:{}", this.host, this.clientId, this.defaultTopic);
    }

    /**
     * Configure and return an MqttPahoClientFactory instance for creating MQTT client connections.
     *
     * @return MqttPahoClientFactory
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        // Set connection options, including server URI, username and password.
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{host});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * Create a MessageChannel for sending MQTT messages.
     *
     * @return MessageChannel
     */
    @Bean(OUTBOUND_CHANNEL)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * Configure the MessageHandler used to send MQTT messages.
     *
     * @return MessageHandler
     */
    @Bean
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL)
    public MessageHandler mqttOutboundHandler() {
        // Use MqttPahoMessageHandler to create a new MQTT client connection for publishing messages.
        final MqttPahoMessageHandler handler = new MqttPahoMessageHandler(clientId + "_pub", mqttClientFactory());
        handler.setDefaultQos(1);
        handler.setDefaultRetained(false);
        handler.setDefaultTopic(defaultTopic);
        handler.setAsync(true);
        return handler;
    }

    /**
     * Create a MessageChannel for receiving MQTT messages.
     *
     * @return MessageChannel
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * Configure the client and subscribed topics,
     * PROPERTY: device property report topic,
     * COMMAND_RESPONSE: topic for command execution results
     *
     * @return MqttPahoMessageDrivenChannelAdapter
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter() {
        final MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId + "_sub",
                mqttClientFactory(), DeviceTopicEnum.PROPERTY.getWildcard(),
                DeviceTopicEnum.COMMAND_RESPONSE.getWildcard()
        );
        adapter.setCompletionTimeout(15000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * Get data through the channel and process the message.
     *
     * @return MessageHandler
     */
    @Bean
    @ServiceActivator(inputChannel = INPUT_CHANNEL)
    public MessageHandler mqttMessageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            if (topic != null) {
                mqttMessageHandlerFactory.getHandlersForTopic(topic).forEach(handler -> {
                    if (log.isDebugEnabled()) {
                        log.debug("Topic: {}, Message content: {}", topic, message.getPayload());
                    }
                    handler.handle(topic, message.getPayload().toString());
                });
            } else {
                log.warn("Received a message whose topic is null.");
            }
        };
    }
}
