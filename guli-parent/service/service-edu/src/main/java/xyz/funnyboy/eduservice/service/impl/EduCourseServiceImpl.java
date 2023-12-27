package xyz.funnyboy.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.EduCourseDescription;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;
import xyz.funnyboy.eduservice.mapper.EduCourseMapper;
import xyz.funnyboy.eduservice.service.EduCourseDescriptionService;
import xyz.funnyboy.eduservice.service.EduCourseService;
import xyz.funnyboy.servicebase.exception.GuliException;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService
{

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 保存课程信息
     *
     * @param courseInfoVO 课程信息 VO
     * @return {@link String}
     */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 保存课程基本信息
        final EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        final boolean save = this.save(eduCourse);
        if (!save) {
            throw new GuliException(ResultCode.ERROR, "添加课程信息失败");
        }

        final String courseId = eduCourse.getId();

        // 保存课程简介
        final EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    /**
     * 获取课程信息
     *
     * @param courseId 课程编号
     * @return {@link CourseInfoVO}
     */
    @Override
    public CourseInfoVO getCourseInfoById(String courseId) {
        // 查询课程基本信息
        final EduCourse eduCourse = this.getById(courseId);
        if (eduCourse == null) {
            throw new GuliException(ResultCode.ERROR, "查询课程信息失败");
        }
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVO);

        // 查询课程简介
        final EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        if (courseDescription != null) {
            courseInfoVO.setDescription(courseDescription.getDescription());
        }

        return courseInfoVO;
    }

    /**
     * 根据 ID 更新课程信息
     *
     * @param courseInfoVO 课程信息 VO
     */
    @Override
    public void updateCourseInfoById(CourseInfoVO courseInfoVO) {
        // 更新课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        final boolean update = this.updateById(eduCourse);
        if (!update) {
            throw new GuliException(ResultCode.ERROR, "更新课程信息失败");
        }

        // 更新课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        final boolean update1 = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!update1) {
            throw new GuliException(ResultCode.ERROR, "更新课程简介失败");
        }
    }
}
