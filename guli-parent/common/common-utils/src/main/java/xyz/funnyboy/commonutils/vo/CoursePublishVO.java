package xyz.funnyboy.commonutils.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程发布信息
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 13:50:29
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVO implements Serializable
{
    private static final long serialVersionUID = -1897312944098108762L;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "类别一级名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "类别二级名称")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private String price;
}
