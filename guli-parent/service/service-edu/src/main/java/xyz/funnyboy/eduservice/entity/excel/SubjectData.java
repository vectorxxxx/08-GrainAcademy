package xyz.funnyboy.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 课程分类数据
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 17:36:58
 */
@Data
public class SubjectData
{
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
