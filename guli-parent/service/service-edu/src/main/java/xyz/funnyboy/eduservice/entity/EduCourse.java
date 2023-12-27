package xyz.funnyboy.eduservice.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.funnyboy.eduservice.entity.base.BaseEntity;

import java.math.BigDecimal;

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
@ApiModel(value = "EduCourse对象",
          description = "课程")
public class EduCourse extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    public static final String COURSE_DRAFT = "Draft";
    public static final String COURSE_NORMAL = "Normal";

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Integer isDeleted;

}
