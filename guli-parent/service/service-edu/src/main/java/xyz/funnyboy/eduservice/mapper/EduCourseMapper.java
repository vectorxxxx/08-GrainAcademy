package xyz.funnyboy.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.vo.CoursePublishVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse>
{
    /**
     * 根据课程ID获取课程发布信息
     *
     * @param courseId 编号
     * @return {@link CoursePublishVO}
     */
    CoursePublishVO selectCoursePublishVOById(String courseId);
}
