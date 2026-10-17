package net.maku.framework.common.utils;

import cn.hutool.core.io.IoUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ExceptionUtility class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class ExceptionUtils {

    /**
     * Get exception info
     * @param e  Exception
     * @return    Return exception info
     */
    public static String getExceptionMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);

        // Close IO stream
        IoUtil.close(pw);
        IoUtil.close(sw);

        return sw.toString();
    }
}