package net.maku.framework.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.idev.excel.FastExcel;
import cn.idev.excel.converters.longconverter.LongStringConverter;
import cn.idev.excel.support.ExcelTypeEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.excel.ExcelDataListener;
import net.maku.framework.common.excel.ExcelFinishCallBack;
import org.apache.commons.lang3.StringUtils;
import net.maku.framework.common.trans.Trans;
import net.maku.framework.common.trans.TransType;
import net.maku.framework.common.trans.TransReflectUtils;
import net.maku.framework.common.trans.TransPojo;
import net.maku.framework.common.trans.DictionaryTransService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * The type Excel utils.
 * {@link <a href="https://idev.cn/fastexcel/zh-CN/docs/write/write_spring"></a>}
 *
 * @author eden
 */
@Slf4j
public class ExcelUtils {

    /**
     * Read excel file
     *
     * @param <T>      Data type
     * @param file     excelFile
     * @param head     Column name
     * @param callBack Callback: the predefined callback interface passed in on import; after the excel data is parsed, the listener passes the data to the callback
     *                 This way, when calling the utility class the imported data can be obtained via the callback; if the data volume is large, it can be split into batches for storage as needed
     */
    public static <T> void readAnalysis(MultipartFile file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            FastExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callBack)).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * Read excel file
     *
     * @param <T>      Data type
     * @param file     excelFile
     * @param head     Column name
     * @param callBack Callback: the predefined callback interface passed in on import; after the excel data is parsed, the listener passes the data to the callback
     *                 This way, when calling the utility class the imported data can be obtained via the callback; if the data volume is large, it can be split into batches for storage as needed
     */
    public static <T> void readAnalysis(File file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            FastExcel.read(new FileInputStream(file), head, new ExcelDataListener<>(callBack)).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * Read excel file synchronously
     *
     * @param <T>   Data type
     * @param file  File
     * @param clazz Template class
     * @return java.util.List
     */
    public static <T> List<T> readSync(File file, Class<T> clazz) {
        return readSync(file, clazz, 1, 0, ExcelTypeEnum.XLSX);
    }

    /**
     * Read excel file synchronously
     *
     * @param <T>       Data type
     * @param file      File
     * @param clazz     Template class
     * @param rowNum    Data start row 1
     * @param sheetNo   Which table
     * @param excelType Data table format type
     * @return java.util.List list
     */
    public static <T> List<T> readSync(File file, Class<T> clazz, Integer rowNum, Integer sheetNo, ExcelTypeEnum excelType) {
        return FastExcel.read(file).headRowNumber(rowNum).excelType(excelType).head(clazz).sheet(sheetNo).doReadSync();
    }


    /**
     * Export data to file
     *
     * @param <T>  Data type
     * @param head Class name
     * @param file Import to file
     * @param data Data
     */
    public static <T> void excelExport(Class<T> head, File file, List<T> data) {
        excelExport(head, file, "sheet1", data);
    }

    /**
     * Export data to file
     *
     * @param <T>       Write format
     * @param head      Class name
     * @param file      Write to file
     * @param sheetName sheetName
     * @param data      Data list
     */
    public static <T> void excelExport(Class<T> head, File file, String sheetName, List<T> data) {
        try {
            FastExcel.write(file, head).sheet(sheetName).registerConverter(new LongStringConverter()).doWrite(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export data to web
     * File download (on failure, an Excel with partial data is returned)
     *
     * @param head      Class name
     * @param excelName excelName
     * @param sheetName sheetName
     * @param data      Data
     */
    public static <T> void excelExport(Class<T> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);

            FastExcel.write(response.getOutputStream(), head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export data to web
     * File download (on failure, an Excel with partial data is returned)
     *
     * @param head      Class name
     * @param excelName excelName
     * @param sheetName sheetName
     * @param data      Data
     */
    public static <T> void excelExport(List<List<String>> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);

            FastExcel.write(response.getOutputStream()).head(head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpServletResponse getExportResponse(String excelName) {
        HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("UTF-8");

        excelName += DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String fileName = URLUtil.encode(excelName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        return response;
    }

    /**
     * Parse dictionary data into fields
     * For example, if T has a genderLabel field with the value male, the gender field needs to be set to 0 automatically
     *
     * @param dataList Data to be reverse parsed
     */
    @SneakyThrows
    public static <T extends TransPojo> void parseDict(List<T> dataList) {
        //No need to initialize when there is no data
        if (CollectionUtil.isEmpty(dataList)) {
            return;
        }
        Class<? extends TransPojo> clazz = dataList.get(0).getClass();
        //Get all fields that need reverse translation
        List<Field> fields = TransReflectUtils.getAnnotationField(clazz, Trans.class);
        //Filter out dictionary translations
        fields = fields.stream().filter(field -> TransType.DICTIONARY.equals(field.getAnnotation(Trans.class).type())).toList();
        DictionaryTransService dictionaryTransService = SpringUtil.getBean(DictionaryTransService.class);
        for (T data : dataList) {
            for (Field field : fields) {
                Trans trans = field.getAnnotation(Trans.class);
                // keyOnly handled automatically when not empty and ref is not empty
                if (StrUtil.isAllNotBlank(trans.key(), trans.ref())) {
                    Field ref = TransReflectUtils.getDeclaredField(clazz, trans.ref());
                    ref.setAccessible(true);
                    // Get dictionary reverse value
                    String value = dictionaryTransService.getDictionaryTransMap().get("un_trans:" + trans.key() + "_" + ref.get(data));
                    if (StringUtils.isBlank(value)) {
                        continue;
                    }
                    // Generally the target field is an int or string field; a separate extraction method was added later
                    if (Integer.class.equals(field.getType())) {
                        field.setAccessible(true);
                        field.set(data, Integer.parseInt(value));
                    } else {
                        field.setAccessible(true);
                        field.set(data, value);
                    }
                }
            }
        }

    }

}
