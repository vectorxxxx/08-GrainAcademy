package xyz.funnyboy.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.vo.CourseWebVO;

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
    CoursePublishVO selectCoursePublishVOById(
            @Param("courseId")
                    String courseId);

    /**
     * 根据课程ID查询课程相关信息
     *
     * @param courseId 课程编号
     * @return {@link CourseWebVO}
     */
    CourseWebVO selectInfoWebById(
            @Param("courseId")
                    String courseId);
}
