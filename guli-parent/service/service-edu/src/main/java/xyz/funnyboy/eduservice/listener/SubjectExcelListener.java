package xyz.funnyboy.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.eduservice.entity.EduSubject;
import xyz.funnyboy.eduservice.entity.excel.SubjectData;
import xyz.funnyboy.eduservice.service.EduSubjectService;
import xyz.funnyboy.servicebase.exception.GuliException;

/**
 * 课程分类Excel监听器
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-26 17:38:17
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData>
{
    private final EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(ResultCode.ERROR, "文件数据不能为空");
        }

        // 一级分类
        final String oneSubjectName = subjectData.getOneSubjectName();
        EduSubject oneSubject = existOneSubject(oneSubjectName);
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(oneSubjectName);
            eduSubjectService.save(oneSubject);
        }

        // 二级分类
        final String twoSubjectName = subjectData.getTwoSubjectName();
        final String pid = oneSubject.getId();
        EduSubject twoSubject = existTwoSubject(twoSubjectName, pid);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(twoSubjectName);
            eduSubjectService.save(twoSubject);
        }
    }

    /**
     * 是否存在一级分类
     *
     * @param oneSubjectName 一级课程名称
     * @return {@link EduSubject}
     */
    private EduSubject existOneSubject(String oneSubjectName) {
        final LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, "0")
                    .eq(EduSubject::getTitle, oneSubjectName);
        return eduSubjectService.getOne(queryWrapper);
    }

    /**
     * 是否存在二级分类
     *
     * @param twoSubjectName 二级课程名称
     * @return {@link EduSubject}
     */
    private EduSubject existTwoSubject(String twoSubjectName, String pid) {
        final LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, pid)
                    .eq(EduSubject::getTitle, twoSubjectName);
        return eduSubjectService.getOne(queryWrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
