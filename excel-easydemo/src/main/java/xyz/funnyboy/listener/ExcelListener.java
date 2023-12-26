package xyz.funnyboy.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import xyz.funnyboy.entity.ReadData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取Excel监听器
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 09:20:19
 */
public class ExcelListener extends AnalysisEventListener<ReadData>
{
    private final List<ReadData> list = new ArrayList<>();

    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("***" + readData);
        list.add(readData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
