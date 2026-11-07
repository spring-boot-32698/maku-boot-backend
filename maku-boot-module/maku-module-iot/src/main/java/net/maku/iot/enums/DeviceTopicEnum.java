package net.maku.iot.enums;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.maku.iot.dto.DeviceClientDTO;

import java.util.Arrays;
import java.util.List;


/**
 * Device message topic type enum
 *
 * @author LSF maku_lsf@163.com
 */
@Getter
@RequiredArgsConstructor
public enum DeviceTopicEnum {
    /**
     * Device command dispatch topic
     */
    COMMAND("command"),

    /**
     * Device info report topic(Command response feedback)
     */
    COMMAND_RESPONSE("command_response"),

    /**
     * Device info report topic(Property related)
     */
    PROPERTY("property");

    /**
     * Device info report topic prefix
     */
    public static final String TOPIC_PREFIX = "/maku/device";


    /**
     * MQTT Topic template, parameters: prefix/clientid(deviceType_tenantId_deviceId_uid)/Device topic
     */
    private static final String TOPIC_TEMPLATE = "{}/{}/{}";

    /**
     * Device topic wildcard template, parameter: prefix/+/Device topic
     */
    private static final String TOPIC_WILDCARD_TEMPLATE = "{}/+/{}";

    /**
     * MQTT Topic
     */
    private final String topic;

    /**
     * Get the device topic wildcard
     *
     * @return Topic wildcard
     */
    public String getWildcard() {
        return StrUtil.format(TOPIC_WILDCARD_TEMPLATE, TOPIC_PREFIX, getTopic());
    }

    /**
     * Build the device topic with full path
     *
     * @param deviceClient Device client info
     * @return Device topic with full path
     */
    public String buildTopic(DeviceClientDTO deviceClient) {
        return StrUtil.format(TOPIC_TEMPLATE, TOPIC_PREFIX, deviceClient.buildClientId(), getTopic());
    }

    /**
     * Parse the specified topic string into a DeviceTopicEnum, throwing IllegalArgumentException if invalid
     *
     * @param topic Topic string
     * @return Corresponding DeviceTopicEnum enum
     * @throws IllegalArgumentException Thrown when the topic string is invalid
     */
    public static DeviceTopicEnum parse(String topic) {
        String topicSuffix = StrUtil.startWith(topic, TOPIC_PREFIX) ? StrUtil.subAfter(topic, "/", true) : topic;
        return Arrays.stream(DeviceTopicEnum.values())
                .filter(d -> d.name().equalsIgnoreCase(topicSuffix)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(StrUtil.format("Invalid DeviceTopicEnum value:{}", topic)));
    }

    /**
     * Check whether the given topic starts with the given prefix
     *
     * @param topic  Topic string
     * @param prefix Prefix string
     * @return Return true if the topic starts with the given prefix, otherwise false
     */
    public static boolean startsWith(String topic, String prefix) {
        try {
            DeviceTopicEnum deviceTopic = parse(topic);
            return deviceTopic.getTopic().startsWith(prefix);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parse the topic and get its full info (device info and topic)
     *
     * @param topic Topic string
     * @return DeviceTopicContext object containing device info and topic
     */
    public static DeviceTopicContext parseContext(String topic) {
        String topicSuffix = StrUtil.subAfter(topic, TOPIC_PREFIX, false);
        List<String> parts = StrUtil.split(topicSuffix, "/", true, true);
        Assert.isTrue(parts.size() == 2, "Invalid device topic:{}", topic);

        DeviceClientDTO deviceClient = DeviceClientDTO.parse(parts.get(0));
        DeviceTopicEnum deviceTopic = DeviceTopicEnum.parse(parts.get(1));

        return DeviceTopicContext.builder()
                .client(deviceClient)
                .topic(deviceTopic)
                .build();
    }

    @Data
    @Builder
    public static class DeviceTopicContext {
        /**
         * Device client info
         */
        private DeviceClientDTO client;

        /**
         * Device topic
         */
        private DeviceTopicEnum topic;
    }
}
