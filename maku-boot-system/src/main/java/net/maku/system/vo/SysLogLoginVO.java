package net.maku.system.vo;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.maku.framework.common.excel.LocalDateTimeConverter;
import net.maku.framework.common.utils.DateUtils;
import net.maku.framework.common.trans.Trans;
import net.maku.framework.common.trans.TransType;
import net.maku.framework.common.trans.TransPojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Login log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@ColumnWidth(20)
@Schema(description = "Login log")
public class SysLogLoginVO implements Serializable, TransPojo {
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @Schema(description = "id")
    private Long id;

    @ExcelProperty("Username")
    @Schema(description = "Username")
    private String username;

    @ExcelProperty("Login IP")
    @Schema(description = "Login IP")
    private String ip;

    @ExcelProperty("Login location")
    @Schema(description = "Login location")
    private String address;

    @ExcelProperty("User Agent")
    @Schema(description = "User Agent")
    private String userAgent;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "success_fail", ref = "statusLabel")
    @Schema(description = "Login status  0: failed   1: succeeded")
    private Integer status;

    @ExcelProperty(value = "Login status")
    private String statusLabel;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "login_operation", ref = "operationLabel")
    @Schema(description = "Operation info   0: login succeeded   1: logout succeeded  2: wrong captcha  3: wrong account or password")
    private Integer operation;

    @ExcelProperty(value = "Operation info")
    private String operationLabel;

    @ExcelProperty(value = "Creation time", converter = LocalDateTimeConverter.class)
    @Schema(description = "Creation time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

}