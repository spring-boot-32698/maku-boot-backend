package net.maku.framework.common.excel;

import java.util.List;

/**
 * excelFinished reading data
 *
 * @param <T> the type parameter
 * @author eden
 */
public interface ExcelFinishCallBack<T> {

    /**
     * Do save batch.
     *
     * @param result the result
     */
    default void doSaveBatch(List<T> result) {
    }
}

