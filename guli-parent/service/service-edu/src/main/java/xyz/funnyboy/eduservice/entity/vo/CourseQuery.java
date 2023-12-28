package xyz.funnyboy.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程查询对象
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 15:08:13
 */
@ApiModel(value = "课程查询对象",
          description = "课程查询对象")
@Data
public class CourseQuery
{
    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "一级类别ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别ID")
    private String sujectId;
}
