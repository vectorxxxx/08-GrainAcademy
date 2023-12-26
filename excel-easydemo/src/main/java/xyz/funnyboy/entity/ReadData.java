package xyz.funnyboy.entity;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 读取数据类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 09:18:18
 */
public class ReadData
{
    @ExcelProperty(index = 0)
    private int sid;
    @ExcelProperty(index = 1)
    private String sname;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "ReadData{" + "sid=" + sid + ", sname='" + sname + '\'' + '}';
    }
}
