package xyz.funnyboy.eduservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.commonutils.BaseEntity;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author vectorx
 * @since 2023-12-23
 */
@ApiModel(value = "EduTeacher对象",
          description = "讲师")
@Data
// 不调用父类的实现
@EqualsAndHashCode(callSuper = false)
// 指定在getter和setter方法中使用链式调用
@Accessors(chain = true)
public class EduTeacher extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
