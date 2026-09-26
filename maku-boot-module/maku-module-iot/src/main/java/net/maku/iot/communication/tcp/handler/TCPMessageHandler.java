package net.maku.iot.communication.tcp.handler;


/**
 * TCPMessage handling interface
 *
 * @author LSF maku_lsf@163.com
 */
public interface TCPMessageHandler {

    /**
     * Whether handling the specified topic is supported
     *
     * @param topic
     * @return
     */
    boolean supports(String topic);

    /**
     * TCPMessage handling interface
     *
     * @param topic
     * @param message
     */
    void handle(String topic, Object message);
}
