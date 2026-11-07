package net.maku.framework.common.excel;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * excelRead listener
 *
 * @author eden
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {
    /**
     * Define a collection that holds all Excel records
     */
    private final List<T> list = new LinkedList<>();
    /**
     * Callback interface
     */
    private final ExcelFinishCallBack<T> callBack;

    /**
     * Construct ExcelFinishCallBack
     *
     * @param callBack ExcelFinishCallBack
     */
    public ExcelDataListener(ExcelFinishCallBack<T> callBack) {
        this.callBack = callBack;
    }


    /**
     * This is called for every row of data parsed
     * Other operations can be done here (filtering, batch inserting...), extend it yourself
     *
     * @param data    one row value. is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
        if (list.size() == 500) {
            this.callBack.doSaveBatch(list);
            list.clear();
        }
    }

    /**
     * Called after all data has been parsed
     * After parsing is complete, store all data in the callback interface
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.callBack.doSaveBatch(list);
    }
}

