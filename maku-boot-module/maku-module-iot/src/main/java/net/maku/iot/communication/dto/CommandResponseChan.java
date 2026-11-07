package net.maku.iot.communication.dto;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Data producer-consumer channel
 */
@Slf4j
public class CommandResponseChan {

    // ConcurrentHashMap of storage channels
    private static final ConcurrentHashMap<String, CommandResponseChan> CHANNEL = new ConcurrentHashMap<>();

    private final CompletableFuture<BaseCommandResponseDTO> future = new CompletableFuture<>();

    private final Long DEFAULT_WAIT_MILLISECONDS = 5 * 1000L;

    // Private constructor, direct external instantiation is not allowed
    private CommandResponseChan() {
    }

    /**
     * Get or create a channel instance
     *
     * @param commandId    Channel identifier
     * @param isNeedCreate Whether a new channel instance needs to be created
     * @return Channel instance
     */
    public static CommandResponseChan getInstance(String commandId, boolean isNeedCreate) {
        if (!isNeedCreate) {
            return CHANNEL.get(commandId);
        }
        return CHANNEL.computeIfAbsent(commandId, k -> new CommandResponseChan());
    }

    /**
     * Get data from the channel, default timeout is 5 seconds
     *
     * @param commandId Channel identifier
     * @return The data obtained; returns null on timeout
     */
    public BaseCommandResponseDTO get(String commandId) {
        return get(commandId, DEFAULT_WAIT_MILLISECONDS);
    }

    /**
     * Get data from the channel, supports timeout setting
     *
     * @param commandId Channel identifier
     * @param timeout   Timeout (milliseconds)
     * @return The data obtained; returns null on timeout
     */
    public BaseCommandResponseDTO get(String commandId, long timeout) {
        CommandResponseChan channel = CHANNEL.get(commandId);
        if (Objects.isNull(channel)) {
            return null;
        }
        try {
            return channel.future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // Timeout exception handling
            log.error("Device response timeout. {}", commandId);
            return null;
        } catch (Exception e) {
            // Other exception handling
            e.printStackTrace();
            return null;
        } finally {
            // Make sure the channel is removed after getting the data
            CHANNEL.remove(commandId, channel);
        }
    }

    /**
     * Put data into the channel and wake up threads that may be waiting for data
     *
     * @param response Data to put in
     */
    public void put(BaseCommandResponseDTO response) {
        String commandId = response.getCommandId();
        if (commandId == null) {
            return;
        }
        CommandResponseChan channel = CHANNEL.get(commandId);
        if (Objects.isNull(channel)) {
            return;
        }
        channel.future.complete(response);
    }
}
