package xyz.funnyboy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.eduservice.entity.EduTeacher;
import xyz.funnyboy.eduservice.entity.vo.EduTeacherQuery;
import xyz.funnyboy.eduservice.mapper.EduTeacherMapper;
import xyz.funnyboy.eduservice.service.EduTeacherService;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-23
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService
{

    /**
     * 条件查询
     *
     * @param pageParam    分页数据
     * @param teacherQuery 查询条件
     */
    @Override
    public IPage<EduTeacher> pageQuery(final Page<EduTeacher> pageParam, final EduTeacherQuery teacherQuery) {
        final QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery == null) {
            return baseMapper.selectPage(pageParam, queryWrapper);
        }

        final String name = teacherQuery.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        final Integer level = teacherQuery.getLevel();
        if (level != null) {
            queryWrapper.eq("level", level);
        }

        final String begin = teacherQuery.getBegin();
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        final String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
