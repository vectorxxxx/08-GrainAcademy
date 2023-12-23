package xyz.funnyboy.serviceedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.servicebase.exception.GuliException;
import xyz.funnyboy.serviceedu.entity.EduTeacher;
import xyz.funnyboy.serviceedu.entity.vo.EduTeacherQuery;
import xyz.funnyboy.serviceedu.service.EduTeacherService;

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
@RequestMapping("/serviceedu/edu-teacher")
// 跨域
@CrossOrigin
public class EduTeacherController
{
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list() {
        try {
            int i = 10 / 0;
        }
        catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, "自定义异常");
        }
        final List<EduTeacher> teacherlist = eduTeacherService.list(null);
        return R.ok()
                .data("items", teacherlist);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable
                    String id) {
        eduTeacherService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
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

