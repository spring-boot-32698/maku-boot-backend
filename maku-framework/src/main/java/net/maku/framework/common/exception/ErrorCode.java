package net.maku.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error code
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(401, "Not authorized yet, cannot access"),
    FORBIDDEN(403, "No permission, access forbidden"),
    REFRESH_TOKEN_INVALID(400, "refresh_token Expired"),
    INTERNAL_SERVER_ERROR(500, "Server error, please try again later");

    private final int code;
    private final String msg;
}
