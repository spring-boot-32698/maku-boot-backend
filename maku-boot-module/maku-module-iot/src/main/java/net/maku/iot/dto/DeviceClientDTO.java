package net.maku.iot.dto;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import net.maku.iot.entity.IotDeviceEntity;
import net.maku.iot.enums.DeviceTypeEnum;

import java.util.List;

/**
 * Device client info
 *
 * @author LSF maku_lsf@163.com
 */
@Data
@Builder
public class DeviceClientDTO {
    /**
     * Format: deviceType_tenantId_deviceId_uid
     */
    private static final String CLIENT_ID_TEMPLATE = "{}_{}_{}_{}";
    private static final String INVALID_CLIENT_ID = "Invalid device clientId:{}";
    /**
     * Tenant id
     */
    private Long tenantId;

    /**
     * Device id
     */
    private Long deviceId;

    /**
     * Device type
     */
    private String deviceType;

    /**
     * Device unique identifier
     */
    private String uid;

    /**
     * Generate clientId
     *
     * @return
     */
    public String buildClientId() {
        if (StrUtil.isBlank(uid)) {
            return null;
        }
        return StrUtil.format(CLIENT_ID_TEMPLATE, deviceType, tenantId, deviceId, uid);
    }

    /**
     * Parse device client info from clientId
     *
     * @param clientId
     * @return
     */
    public static DeviceClientDTO parse(String clientId) {
        List<String> clientIdParts = StrUtil.split(clientId, "_");
        Assert.isTrue(clientIdParts.size() > 3, INVALID_CLIENT_ID, clientId);
        return DeviceClientDTO.builder()
                .deviceType(clientIdParts.get(0))
                .tenantId(Long.valueOf(clientIdParts.get(1)))
                .deviceId(Long.valueOf(clientIdParts.get(2)))
                .uid(clientIdParts.get(3))
                .build();
    }

    /**
     * Create deviceClient from Device
     *
     * @param device
     * @return
     */
    public static DeviceClientDTO from(IotDeviceEntity device) {
        return DeviceClientDTO.builder()
                .deviceType(DeviceTypeEnum.parse(device.getType().toString()).name().toLowerCase())
                .tenantId(device.getTenantId() == null ? 0 : device.getTenantId())
                .deviceId(device.getId())
                .uid(device.getUid())
                .build();
    }
}
