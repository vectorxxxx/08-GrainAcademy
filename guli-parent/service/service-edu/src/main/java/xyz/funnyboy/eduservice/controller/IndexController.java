package xyz.funnyboy.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.EduCourse;
import xyz.funnyboy.eduservice.entity.EduTeacher;
import xyz.funnyboy.eduservice.service.EduCourseService;
import xyz.funnyboy.eduservice.service.EduTeacherService;

import java.util.List;

/**
 * 课程名师接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 21:54:01
 */
@Api(description = "课程名师接口")
@RestController
@RequestMapping("/eduservice")
// @CrossOrigin
public class IndexController
{
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "查询前8条热门课程，查询前4条名师")
    @GetMapping("index")
    public R index() {
        // 查询前8条热门课程
        final LambdaQueryWrapper<EduCourse> courseWrapper = new LambdaQueryWrapper<>();
        courseWrapper.orderByDesc(EduCourse::getId)
                     .last("limit 8");
        final List<EduCourse> courseList = courseService.list(courseWrapper);

        // 查询前4条名师
        final LambdaQueryWrapper<EduTeacher> teacherWrapper = new LambdaQueryWrapper<>();
        teacherWrapper.orderByDesc(EduTeacher::getId)
                      .last("limit 4");
        final List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return R.ok()
                .data("courseList", courseList)
                .data("teacherList", teacherList);
    }
}
