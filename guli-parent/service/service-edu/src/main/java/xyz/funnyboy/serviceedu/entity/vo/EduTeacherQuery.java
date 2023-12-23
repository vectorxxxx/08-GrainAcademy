package xyz.funnyboy.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 讲师查询对象封装
 *
 * @author VectorX
 * @version V1.0
 * @date 23/12/2023
 */
@ApiModel(value = "Teacher查询对象",
          description = "讲师查询对象封装")
@Data
public class EduTeacherQuery
{
    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",
                      example = "2023-12-23 20:53:04")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",
                      example = "2023-12-23 20:53:10")
    private String end;
}
