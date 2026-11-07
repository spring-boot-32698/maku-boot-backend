package net.maku;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;
import net.maku.framework.common.excel.ExcelFinishCallBack;
import net.maku.framework.common.excel.LocalDateTimeConverter;
import net.maku.framework.common.utils.ExcelUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * FastExcel Test
 *
 * @author eden
 */
public class FastExcelTest {

    @Test
    public void doImport() {
        File file = new File("D://upload//test01.xlsx");
        ExcelClass excelClass = new ExcelClass();
        excelClass.setNumber(1);
        excelClass.setDecimals(1.2);
        excelClass.setString("test");
        excelClass.setDate(LocalDateTime.now());
        List<ExcelClass> data = Arrays.asList(excelClass, excelClass, excelClass);
        if (file.exists()) {
            ExcelUtils.excelExport(ExcelClass.class, file, data);
            List<ExcelClass> list = ExcelUtils.readSync(file, ExcelClass.class);
            list.forEach(System.out::println);
        }
    }


    @Test
    public void doAImport() {
        File file = new File("D://upload//test01.xlsx");
        ExcelClass excelClass = new ExcelClass();
        excelClass.setNumber(1);
        excelClass.setDecimals(1.2);
        excelClass.setString("test");
        excelClass.setDate(LocalDateTime.now());
        List<ExcelClass> data = Arrays.asList(excelClass, excelClass, excelClass, excelClass, excelClass, excelClass, excelClass);
        if (file.exists()) {
            ExcelUtils.excelExport(ExcelClass.class, file, data);
            ExcelUtils.readAnalysis(file, ExcelClass.class, new ServiceA());
        }

    }

    @Data
    public static class ExcelClass {

        @ExcelProperty("Integer")
        private Integer number;

        @ExcelProperty("String")
        private String string;

        @ExcelProperty("Decimal")
        private Double decimals;

        @ExcelProperty(value = "Date", converter = LocalDateTimeConverter.class)
        private LocalDateTime date;
    }

}

class ServiceA implements ExcelFinishCallBack<FastExcelTest.ExcelClass> {

    @Override
    public void doSaveBatch(List<FastExcelTest.ExcelClass> result) {
        System.out.println(result.size());
    }
}

