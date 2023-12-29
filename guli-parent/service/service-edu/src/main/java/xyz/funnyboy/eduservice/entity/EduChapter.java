package xyz.funnyboy.eduservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.commonutils.BaseEntity;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EduChapter对象",
          description = "课程")
public class EduChapter extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;
}
