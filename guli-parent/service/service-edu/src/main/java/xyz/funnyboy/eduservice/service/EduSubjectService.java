package xyz.funnyboy.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.eduservice.entity.EduSubject;
import xyz.funnyboy.eduservice.entity.vo.SubjectNestedVO;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
public interface EduSubjectService extends IService<EduSubject>
{

    /**
     * 添加课程
     *
     * @param file 文件
     */
    void importSubjectData(MultipartFile file);

    /**
     * 嵌套列表
     *
     * @return {@link List}<{@link SubjectNestedVO}>
     */
    List<SubjectNestedVO> nestedList();
}
