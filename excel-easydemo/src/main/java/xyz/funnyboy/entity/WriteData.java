package xyz.funnyboy.entity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 写入数据类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 09:01:09
 */
public class WriteData
{
    @ExcelProperty("学生编号")
    private int sno;

    @ExcelProperty("学生姓名")
    private String name;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoData{" + "sno=" + sno + ", name='" + name + '\'' + '}';
    }
}
