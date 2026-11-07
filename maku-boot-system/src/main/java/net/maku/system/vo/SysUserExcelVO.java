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
 * Excel user table
 * <p>
 * Columns are bound by position (index), not header text, so import accepts both the current
 * English template and older files with Chinese headers (用户名, 姓名, 性别, ...).
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

    @ExcelProperty(value = "Username", index = 0)
    private String username;

    @ExcelProperty(value = "Name", index = 1)
    private String realName;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_gender", ref = "genderLabel")
    private Integer gender;

    @ExcelProperty(value = "Gender", index = 2)
    private String genderLabel;

    @ExcelProperty(value = "Email", index = 3)
    private String email;

    @ExcelProperty(value = "Mobile number", index = 4)
    private String mobile;

    @ExcelProperty(value = "Organization ID", index = 5)
    private Long orgId;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_status", ref = "statusLabel")
    private Integer status;

    @ExcelProperty(value = "Status", index = 6)
    private String statusLabel;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = "user_super_admin", ref = "superAdminLabel")
    private Integer superAdmin;

    @ExcelProperty(value = "Super administrator", index = 7)
    private String superAdminLabel;

    @ExcelProperty(value = "Creation time", index = 8, converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

}
