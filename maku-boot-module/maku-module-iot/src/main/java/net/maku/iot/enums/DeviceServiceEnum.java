package net.maku.iot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeviceServiceEnum {

    COMMAND_ID("Command ID");

    /**
     * Type value
     */
    private final String value;


}
