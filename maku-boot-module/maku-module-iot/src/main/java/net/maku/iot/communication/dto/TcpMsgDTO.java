package net.maku.iot.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * tcpCommunication data packet
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Schema(description = "tcpCommunication data packet wrapper class")
public class TcpMsgDTO {
    private String topic;
    private Object msg;
}
