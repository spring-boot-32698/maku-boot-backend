package net.maku.framework.common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Pagination utility class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "Pagination data")
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Total number of records")
    private int total;

    @Schema(description = "List data")
    private List<T> list;

    @Schema(description = "Total data")
    private Map<String, Object> summary;

    /**
     * Pagination
     * @param list   List data
     * @param total  Total number of records
     */
    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }

    /**
     * Pagination
     * @param list   List data
     * @param total  Total number of records
     * @param summary Total data
     */
    public PageResult(List<T> list, long total, Map<String, Object> summary) {
        this.list = list;
        this.total = (int)total;
        this.summary = summary;
    }
}