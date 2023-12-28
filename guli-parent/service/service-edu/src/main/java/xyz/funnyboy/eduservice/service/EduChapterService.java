package xyz.funnyboy.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.eduservice.entity.EduChapter;
import xyz.funnyboy.eduservice.entity.vo.ChapterVO;

import java.util.List;

/**
 * 章节Service
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
public interface EduChapterService extends IService<EduChapter>
{
    /**
     * 获取课程章节嵌套列表
     *
     * @param courseId 课程编号
     * @return {@link List}<{@link ChapterVO}>
     */
    List<ChapterVO> getChapterVideo(String courseId);
}
