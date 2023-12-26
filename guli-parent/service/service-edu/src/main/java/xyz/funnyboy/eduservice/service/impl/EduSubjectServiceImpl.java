package xyz.funnyboy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.eduservice.entity.EduSubject;
import xyz.funnyboy.eduservice.entity.excel.SubjectData;
import xyz.funnyboy.eduservice.entity.vo.SubjectNestedVO;
import xyz.funnyboy.eduservice.listener.SubjectExcelListener;
import xyz.funnyboy.eduservice.mapper.EduSubjectMapper;
import xyz.funnyboy.eduservice.service.EduSubjectService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService
{

    /**
     * 添加课程
     *
     * @param file 文件
     */
    @Override
    public void importSubjectData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new SubjectExcelListener(this))
                     .sheet()
                     .doRead();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 嵌套列表
     *
     * @return {@link List}<{@link SubjectNestedVO}>
     */
    @Override
    public List<SubjectNestedVO> nestedList() {
        List<SubjectNestedVO> oneSubjectNestedVOList = new ArrayList<>();

        // 一级分类数据记录
        final LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, "0")
                    .orderByAsc(EduSubject::getSort, EduSubject::getId);
        final List<EduSubject> oneSubjectList = this.list(queryWrapper);

        // 二级分类数据记录
        final LambdaQueryWrapper<EduSubject> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.ne(EduSubject::getParentId, "0")
                     .orderByAsc(EduSubject::getSort, EduSubject::getId);
        final List<EduSubject> twoSubjectList = this.list(queryWrapper2);

        for (EduSubject oneSubject : oneSubjectList) {
            // 一级分类VO
            SubjectNestedVO oneSubjectNestedVO = new SubjectNestedVO();
            BeanUtils.copyProperties(oneSubject, oneSubjectNestedVO);

            // 二级分类VO
            List<SubjectNestedVO> twoSubjectNestedVOList = new ArrayList<>();
            for (EduSubject twoSubject : twoSubjectList) {
                if (twoSubject.getParentId()
                              .equals(oneSubject.getId())) {
                    SubjectNestedVO twoSubjectNestedVO = new SubjectNestedVO();
                    BeanUtils.copyProperties(twoSubject, twoSubjectNestedVO);
                    twoSubjectNestedVOList.add(twoSubjectNestedVO);
                }
            }

            oneSubjectNestedVO.setChildren(twoSubjectNestedVOList);
            oneSubjectNestedVOList.add(oneSubjectNestedVO);
        }
        return oneSubjectNestedVOList;
    }
}
