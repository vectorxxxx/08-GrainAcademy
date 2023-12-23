package xyz.funnyboy.serviceedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.serviceedu.entity.EduTeacher;
import xyz.funnyboy.serviceedu.entity.vo.EduTeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-23
 */
public interface EduTeacherService extends IService<EduTeacher>
{

    /**
     * 条件查询
     *
     * @param pageParam    分页数据
     * @param teacherQuery 查询条件
     */
    IPage<EduTeacher> pageQuery(Page<EduTeacher> pageParam, EduTeacherQuery teacherQuery);
}
