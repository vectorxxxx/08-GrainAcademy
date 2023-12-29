package xyz.funnyboy.eduservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.commonutils.BaseEntity;

/**
 * <p>
 * 课程科目
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@ApiModel(value = "EduSubject对象",
          description = "课程科目")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EduSubject extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;
}
