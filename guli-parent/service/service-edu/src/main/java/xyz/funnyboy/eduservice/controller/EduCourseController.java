package xyz.funnyboy.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.vo.ChapterVO;
import xyz.funnyboy.eduservice.entity.vo.CourseInfoVO;
import xyz.funnyboy.eduservice.entity.vo.CourseQueryVO;
import xyz.funnyboy.eduservice.entity.vo.CourseWebVO;
import xyz.funnyboy.eduservice.service.EduChapterService;
import xyz.funnyboy.eduservice.service.EduCourseService;

import java.util.List;

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

    @Autowired
    private EduChapterService eduChapterService;

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
        final String id = eduCourseService.updateCourseInfoById(courseInfoVO);
        return R.ok()
                .data("courseId", id);
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

    @ApiOperation(value = "根据条件查询课程信息")
    @PostMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    long limit,

            @ApiParam(name = "searchObj",
                      value = "查询条件",
                      required = false)
            @RequestBody
                    CourseQueryVO courseQueryVO) {
        final Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQueryVO);
        return R.ok()
                .data("total", pageParam.getTotal())
                .data("current", pageParam.getCurrent())
                .data("pages", pageParam.getPages())
                .data("size", pageParam.getSize())
                .data("hasPrevious", pageParam.hasPrevious())
                .data("hasNext", pageParam.hasNext())
                .data("rows", pageParam.getRecords());
    }

    @ApiOperation(value = "根据课程id删除课程")
    @DeleteMapping("{courseId}")
    public R removeCourseById(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable
                    String courseId) {
        eduCourseService.removeCourseById(courseId);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询课程相关信息")
    @GetMapping("{courseId}")
    public R getCourseInfoWeb(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable
                    String courseId) {
        // 课程（包含讲师、分类等相关信息）
        final CourseWebVO courseWebVO = eduCourseService.selectInfoWebById(courseId);
        // 查询课程章节信息
        final List<ChapterVO> chapterVOList = eduChapterService.getChapterVideo(courseId);
        return R.ok()
                .data("course", courseWebVO)
                .data("chapterVOList", chapterVOList);
    }

    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/remote/{courseId}")
    public CoursePublishVO getCourseInfoRemote(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable("courseId")
                    String courseId) {
        return eduCourseService.getCoursePublishVOById(courseId);
    }
}

