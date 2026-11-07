package net.maku.framework.common.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * XSS Filter utility class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class XssUtils {
    /**
     * Do not format
     */
    private final static Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    /**
     * XSSFilter
     *
     * @param content Content to filter
     * @return Return the filtered content
     */
    public static String filter(String content) {
        return Jsoup.clean(content, "", Safelist.relaxed(), outputSettings);
    }

}