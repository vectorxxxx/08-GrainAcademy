package xyz.funnyboy.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程章节VO
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-27 21:31:54
 */
@ApiModel(value = "ChapterVO对象",
          description = "课程章节")
@Data
public class ChapterVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private List<VideoVO> children;
}
