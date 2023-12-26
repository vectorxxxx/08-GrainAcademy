package xyz.funnyboy.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import xyz.funnyboy.entity.ReadData;
import xyz.funnyboy.listener.ExcelListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 测试读取数据
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 09:19:40
 */
public class TestReadData
{
    public static void main(String[] args) throws FileNotFoundException {
        // 写法一
        String fileName = "D:\\workspace-mine\\08-GrainAcademy\\excel-easydemo\\11.xlsx";
        EasyExcel.read(fileName, ReadData.class, new ExcelListener())
                 .sheet()
                 .doRead();

        System.out.println("==============================================================");
        
        // 写法二
        final BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\workspace-mine\\08-GrainAcademy\\excel-easydemo\\22.xlsx"));
        final ExcelReader excelReader = EasyExcel.read(inputStream, ReadData.class, new ExcelListener())
                                                 .build();
        final ReadSheet readSheet = EasyExcel.readSheet(0)
                                             .build();
        excelReader.read(readSheet);
        excelReader.finish();
    }
}
