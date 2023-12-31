package xyz.funnyboy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.EduCourseDescription;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;
import xyz.funnyboy.eduservice.entity.vo.CourseQueryVO;
import xyz.funnyboy.eduservice.entity.vo.CourseWebVO;
import xyz.funnyboy.eduservice.mapper.EduCourseMapper;
import xyz.funnyboy.eduservice.service.EduChapterService;
import xyz.funnyboy.eduservice.service.EduCourseDescriptionService;
import xyz.funnyboy.eduservice.service.EduCourseService;
import xyz.funnyboy.eduservice.service.EduVideoService;
import xyz.funnyboy.servicebase.exception.GuliException;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

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
        eduCourse.setStatus(EduCourse.COURSE_DRAFT);
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
    public String updateCourseInfoById(CourseInfoVO courseInfoVO) {
        // 更新课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        final boolean update = this.updateById(eduCourse);
        if (!update) {
            throw new GuliException(ResultCode.ERROR, "更新课程信息失败");
        }

        final String id = courseInfoVO.getId();

        // 更新课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        final boolean update1 = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!update1) {
            throw new GuliException(ResultCode.ERROR, "更新课程简介失败");
        }

        return id;
    }

    /**
     * 根据课程ID获取课程发布信息
     *
     * @param courseId 编号
     * @return {@link CoursePublishVO}
     */
    @Override
    public CoursePublishVO getCoursePublishVOById(String courseId) {
        return baseMapper.selectCoursePublishVOById(courseId);
    }

    /**
     * 发布课程
     *
     * @param courseId 课程编号
     */
    @Override
    public void publishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        final boolean update = this.updateById(eduCourse);
        if (!update) {
            throw new GuliException(ResultCode.ERROR, "发布课程失败");
        }
    }

    /**
     * 分页查询课程信息
     *
     * @param pageParam     页面参数
     * @param courseQueryVO 查询条件
     */
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQueryVO courseQueryVO) {
        final LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        if (courseQueryVO == null) {
            this.page(pageParam, queryWrapper);
            return;
        }

        // 课程标题
        final String title = courseQueryVO.getTitle();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like(EduCourse::getTitle, title);
        }

        // 讲师ID
        final String teacherId = courseQueryVO.getTeacherId();
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq(EduCourse::getTeacherId, teacherId);
        }

        // 一级类别ID
        final String subjectParentId = courseQueryVO.getSubjectParentId();
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq(EduCourse::getSubjectParentId, subjectParentId);
        }

        // 二级类别ID
        final String sujectId = courseQueryVO.getSubjectId();
        if (!StringUtils.isEmpty(sujectId)) {
            queryWrapper.eq(EduCourse::getSubjectId, sujectId);
        }

        // 销售数量排序
        if (!StringUtils.isEmpty(courseQueryVO.getBuyCountSort())) {
            queryWrapper.orderByDesc(EduCourse::getBuyCount);
        }

        // 销售数量排序
        if (!StringUtils.isEmpty(courseQueryVO.getGmtCreateSort())) {
            queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }

        // 价格排序
        if (!StringUtils.isEmpty(courseQueryVO.getPriceSort())) {
            queryWrapper.orderByDesc(EduCourse::getPrice);
        }

        this.page(pageParam, queryWrapper);
    }

    /**
     * 按 ID 删除课程
     *
     * @param courseId 课程编号
     */
    @Override
    public void removeCourseById(String courseId) {
        // 按 ID 删除所有视频
        eduVideoService.removeByCourseId(courseId);

        // 按 ID 删除所有章节
        eduChapterService.removeByCourseId(courseId);

        // 按 ID 删除课程简介
        eduCourseDescriptionService.removeById(courseId);

        // 按 ID 删除课程
        this.removeById(courseId);
    }

    /**
     * 按教师 ID 查询课程
     *
     * @param teacherId 教师 ID
     * @return {@link List}<{@link CourseInfoVO}>
     */
    @Override
    public List<CourseInfoVO> selectByTeacherId(String teacherId) {
        final LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<EduCourse>().eq(EduCourse::getTeacherId, teacherId)
                                                                                              .orderByDesc(EduCourse::getGmtModified);
        final List<EduCourse> courseList = this.list(queryWrapper);

        return courseList.stream()
                         .map(eduCourse -> {
                             CourseInfoVO courseInfoVO = new CourseInfoVO();
                             BeanUtils.copyProperties(eduCourse, courseInfoVO);
                             return courseInfoVO;
                         })
                         .collect(Collectors.toList());
    }

    /**
     * 根据课程ID查询课程相关信息
     *
     * @param courseId 课程编号
     * @return {@link CourseWebVO}
     */
    @Override
    public CourseWebVO selectInfoWebById(String courseId) {
        // 更新课程浏览次数
        this.updatePageViewCount(courseId);
        return baseMapper.selectInfoWebById(courseId);
    }

    /**
     * 更新课程浏览次数
     *
     * @param courseId 课程编号
     */
    @Override
    public void updatePageViewCount(String courseId) {
        EduCourse course = this.getById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        this.updateById(course);
    }
}
