package xyz.funnyboy.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import xyz.funnyboy.entity.WriteData;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试写入数据
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 09:17:38
 */
public class TestWriteData
{
    private static List<WriteData> data() {
        final ArrayList<WriteData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WriteData writeData = new WriteData();
            writeData.setSno(i);
            writeData.setName("zhangsn" + i);
            list.add(writeData);
        }
        return list;
    }

    public static void main(String[] args) {
        // 写入写法一
        String fileName = "D:\\workspace-mine\\08-GrainAcademy\\excel-easydemo\\11.xlsx";
        EasyExcel.write(fileName)
                 .sheet("写入写法一")
                 .doWrite(data());
        // 写入写法二
        String fileName2 = "D:\\workspace-mine\\08-GrainAcademy\\excel-easydemo\\22.xlsx";
        final ExcelWriter excelWriter = EasyExcel.write(fileName2)
                                                 .build();
        final WriteSheet writeSheet = EasyExcel.writerSheet("写入写法二")
                                               .build();
        excelWriter.write(data(), writeSheet);
        excelWriter.finish();
    }
}
