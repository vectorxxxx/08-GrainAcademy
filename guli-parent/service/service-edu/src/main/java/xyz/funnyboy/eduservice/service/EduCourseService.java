package xyz.funnyboy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;
import xyz.funnyboy.eduservice.entity.vo.CoursePublishVO;
import xyz.funnyboy.eduservice.entity.vo.CourseQuery;

import java.util.List;

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
    String updateCourseInfoById(CourseInfoVO courseInfoVO);

    /**
     * 根据课程ID获取课程发布信息
     *
     * @param courseId 编号
     * @return {@link CoursePublishVO}
     */
    CoursePublishVO getCoursePublishVOById(String courseId);

    /**
     * 发布课程
     *
     * @param courseId 课程编号
     */
    void publishCourse(String courseId);

    /**
     * 页面查询 分页查询课程信息
     *
     * @param pageParam   页面参数
     * @param courseQuery 查询条件
     */
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    /**
     * 按 ID 删除课程
     *
     * @param courseId 课程编号
     */
    void removeCourseById(String courseId);

    /**
     * 按教师 ID 查询课程
     *
     * @param teacherId 教师 ID
     * @return {@link List}<{@link CourseInfoVO}>
     */
    List<CourseInfoVO> selectByTeacherId(String teacherId);
}
