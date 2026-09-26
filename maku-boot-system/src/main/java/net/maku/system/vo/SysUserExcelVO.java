package net.maku.system.vo;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import net.maku.framework.common.excel.LocalDateTimeConverter;
import net.maku.framework.common.trans.Trans;
import net.maku.framework.common.trans.TransType;
import net.maku.framework.common.trans.TransPojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * excelUser table
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@ColumnWidth(20)
public class SysUserExcelVO implements Serializable, TransPojo {
    private static final long serialVersionUID = 1L;

    /**
     * This property is useless for export and is only used for translation
     */
    @ExcelIgnore
    private Long id;

    @ExcelProperty("Username")
    private String username;

    @ExcelProperty("Name")
    private String realName;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_gender", ref = "genderLabel")
    private Integer gender;

    @ExcelProperty(value = "Gender")
    private String genderLabel;

    @ExcelProperty("Email")
    private String email;

    @ExcelProperty("Mobile number")
    private String mobile;

    @ExcelProperty("Organization ID")
    private Long orgId;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_status", ref = "statusLabel")
    private Integer status;

    @ExcelProperty(value = "Status")
    private String statusLabel;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_super_admin", ref = "superAdminLabel")
    private Integer superAdmin;

    @ExcelProperty(value = "Super administrator")
    private String superAdminLabel;

    @ExcelProperty(value = "Creation time", converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

}
