package xyz.funnyboy.eduservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;
import xyz.funnyboy.eduservice.entity.vo.CoursePublishVO;
import xyz.funnyboy.eduservice.service.EduCourseService;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController
{
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(
            @ApiParam(name = "courseInfoVO",
                      value = "课程信息",
                      required = true)
            @RequestBody
                    CourseInfoVO courseInfoVO) {
        final String courseId = eduCourseService.saveCourseInfo(courseInfoVO);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok()
                    .data("courseId", courseId);
        }
        else {
            return R.error()
                    .message("保存失败");
        }
    }

    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable
                    String courseId) {
        final CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoById(courseId);
        return R.ok()
                .data("item", courseInfoVO);
    }

    @ApiOperation(value = "更新课程信息")
    @PutMapping("updateCourseInfo")
    public R updateCourseInfo(
            @ApiParam(name = "courseInfoVO",
                      value = "课程信息",
                      required = true)
            @RequestBody
                    CourseInfoVO courseInfoVO) {
        eduCourseService.updateCourseInfoById(courseInfoVO);
        return R.ok()
                .data("courseId", courseInfoVO.getId());
    }

    @ApiOperation(value = "根据课程ID获取课程发布信息")
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishVOById(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable
                    String courseId) {
        final CoursePublishVO coursePublishVO = eduCourseService.getCoursePublishVOById(courseId);
        return R.ok()
                .data("item", coursePublishVO);
    }

    @ApiOperation(value = "根据课程ID发布课程")
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable
                    String courseId) {
        eduCourseService.publishCourse(courseId);
        return R.ok();
    }
}

