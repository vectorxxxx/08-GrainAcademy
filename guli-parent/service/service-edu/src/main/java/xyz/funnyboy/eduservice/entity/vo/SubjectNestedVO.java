package xyz.funnyboy.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程分类VO
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 19:58:08
 */
@Data
public class SubjectNestedVO
{
    private String id;
    private String title;
    private List<SubjectNestedVO> children = new ArrayList<>();
}
