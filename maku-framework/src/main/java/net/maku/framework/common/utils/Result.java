package net.maku.framework.common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.exception.ErrorCode;

/**
 * Response data
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Response")
public class Result<T> {
    @Schema(description = "Code: 0 means success, other values mean failure")
    private int code = 0;

    @Schema(description = "Message content")
    private String msg = "success";

    @Schema(description = "Response data")
    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> error(String msg) {
        return error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
