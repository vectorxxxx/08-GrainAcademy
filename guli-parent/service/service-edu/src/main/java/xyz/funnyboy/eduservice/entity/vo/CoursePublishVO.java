package xyz.funnyboy.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
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
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
