package xyz.funnyboy.eduservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.vo.SubjectNestedVO;
import xyz.funnyboy.eduservice.service.EduSubjectService;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController
{
    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation(value = "添加课程")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.importSubjectData(file);
        return R.ok();
    }

    @ApiOperation(value = "嵌套课程列表")
    @GetMapping("")
    public R nestedList() {
        List<SubjectNestedVO> subjectNestedVOList = eduSubjectService.nestedList();
        return R.ok()
                .data("items", subjectNestedVOList);
    }
}

