package xyz.funnyboy.eduservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.EduChapter;
import xyz.funnyboy.eduservice.entity.vo.ChapterVO;
import xyz.funnyboy.eduservice.service.EduChapterService;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Api(description = "课程大纲")
@RestController
@RequestMapping("/eduservice/chapter")
// @CrossOrigin
public class EduChapterController
{
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "获取章节课时信息")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable("courseId")
                    String courseId) {
        final List<ChapterVO> chapterVOList = eduChapterService.getChapterVideo(courseId);
        return R.ok()
                .data("items", chapterVOList);
    }

    @ApiOperation(value = "根据ID获取章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id",
                      value = "章节id",
                      required = true)
            @PathVariable
                    String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok()
                .data("item", eduChapter);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("")
    public R save(
            @ApiParam(name = "chapter",
                      value = "章节信息",
                      required = true)
            @RequestBody
                    EduChapter chapter) {
        final boolean save = eduChapterService.save(chapter);
        return save ?
               R.ok() :
               R.error()
                .message("添加章节错误");
    }

    @ApiOperation(value = "更新章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id",
                      value = "章节ID",
                      required = true)
            @PathVariable
                    String id,
            @ApiParam(name = "chapter",
                      value = "章节信息",
                      required = true)
            @RequestBody
                    EduChapter chapter) {
        chapter.setId(id);
        final boolean updateById = eduChapterService.updateById(chapter);
        return updateById ?
               R.ok() :
               R.error()
                .message("更新章节错误");
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id",
                      value = "章节id",
                      required = true)
            @PathVariable
                    String id) {
        final boolean removeById = eduChapterService.removeById(id);
        return removeById ?
               R.ok() :
               R.error()
                .message("删除章节错误");
    }
}

