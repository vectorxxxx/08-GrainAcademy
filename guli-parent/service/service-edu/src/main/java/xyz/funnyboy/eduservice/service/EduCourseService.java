package xyz.funnyboy.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
public interface EduCourseService extends IService<EduCourse>
{

    /**
     * 保存课程信息
     *
     * @param courseInfoVO 课程信息 VO
     * @return {@link String}
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 获取课程信息
     *
     * @param courseId 课程编号
     * @return {@link CourseInfoVO}
     */
    CourseInfoVO getCourseInfoById(String courseId);

    /**
     * 根据 ID 更新课程信息
     *
     * @param courseInfoVO 课程信息 VO
     */
    void updateCourseInfoById(CourseInfoVO courseInfoVO);
}
