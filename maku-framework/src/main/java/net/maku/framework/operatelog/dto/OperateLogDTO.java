package net.maku.framework.operatelog.dto;


import lombok.Data;

/**
 * Operation log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class OperateLogDTO {
    /**
     * User ID
     */
    private Long userId;

    /**
     * Operator
     */
    private String realName;

    /**
     * Module name
     */
    private String module;

    /**
     * Operation name
     */
    private String name;

    /**
     * Request URI
     */
    private String reqUri;

    /**
     * Request method
     */
    private String reqMethod;

    /**
     * Request parameters
     */
    private String reqParams;

    /**
     * Operation IP
     */
    private String ip;

    /**
     * Login location
     */
    private String address;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * Operation type
     */
    private Integer operateType;

    /**
     * Execution duration
     */
    private Integer duration;

    /**
     * Operation status
     */
    private Integer status;

    /**
     * Return message
     */
    private String resultMsg;

    /**
     * Tenant ID
     */
    private Long tenantId;
}
