package xyz.funnyboy.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.EduTeacher;
import xyz.funnyboy.eduservice.entity.vo.EduTeacherQuery;
import xyz.funnyboy.eduservice.service.EduTeacherService;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
// 跨域
@CrossOrigin
public class EduTeacherController
{
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list() {
        // try {
        //     int i = 10 / 0;
        // }
        // catch (Exception e) {
        //     throw new GuliException(ResultCode.ERROR, "自定义异常");
        // }
        final List<EduTeacher> teacherlist = eduTeacherService.list(null);
        return R.ok()
                .data("items", teacherlist);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable
                    String id) {
        final boolean remove = eduTeacherService.removeById(id);
        return remove ?
               R.ok() :
               R.error()
                .message("删除失败");
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable("page")
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable("limit")
                    Long limit,

            @ApiParam(name = "teacherQuery",
                      value = "查询对象",
                      required = true)
            @RequestBody(required = false)
                    EduTeacherQuery teacherQuery) {
        final Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.pageQuery(pageParam, teacherQuery);

        final long total = pageParam.getTotal();
        final List<EduTeacher> records = pageParam.getRecords();

        return R.ok()
                .data("total", total)
                .data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "eduTeacher",
                      value = "讲师对象",
                      required = true)
            @RequestBody
                    EduTeacher eduTeacher) {
        eduTeacherService.save(eduTeacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @PathVariable
                    String id) {
        final EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok()
                .data("item", eduTeacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @PathVariable
            final String id,

            @ApiParam(name = "eduTeacher",
                      value = "讲师对象",
                      required = true)
            @RequestBody
                    EduTeacher eduTeacher) {
        eduTeacherService.updateById(eduTeacher);
        return R.ok();
    }
}

