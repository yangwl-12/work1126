package com.yang;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yang.entity.DemoData;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<DemoData> {
    List list = new ArrayList<>();

    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {

        //每行读取完成后会调用invoke方法
        System.out.println(data);
        list.add(data);

    }

    //全部读取完成后执行 doAfter方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("over");

    }
}
