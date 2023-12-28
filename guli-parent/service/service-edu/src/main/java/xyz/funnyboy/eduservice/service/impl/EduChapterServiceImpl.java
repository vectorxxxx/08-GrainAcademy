package xyz.funnyboy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.eduservice.entity.EduChapter;
import xyz.funnyboy.eduservice.entity.EduVideo;
import xyz.funnyboy.eduservice.entity.vo.ChapterVO;
import xyz.funnyboy.eduservice.entity.vo.VideoVO;
import xyz.funnyboy.eduservice.mapper.EduChapterMapper;
import xyz.funnyboy.eduservice.service.EduChapterService;
import xyz.funnyboy.eduservice.service.EduVideoService;
import xyz.funnyboy.servicebase.exception.GuliException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService
{

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 获取课程章节嵌套列表
     *
     * @param courseId 课程编号
     * @return {@link List}<{@link ChapterVO}>
     */
    @Override
    public List<ChapterVO> getChapterVideo(String courseId) {
        List<ChapterVO> chapterVOList = new ArrayList<>();

        // 获取章节信息
        LambdaQueryWrapper<EduChapter> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(EduChapter::getCourseId, courseId)
                      .orderByAsc(EduChapter::getSort, EduChapter::getId);
        final List<EduChapter> chapterList = this.list(chapterWrapper);

        // 获取课时信息
        LambdaQueryWrapper<EduVideo> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(EduVideo::getCourseId, courseId)
                    .orderByAsc(EduVideo::getSort, EduVideo::getId);
        final List<EduVideo> videoList = eduVideoService.list(videoWrapper);

        for (EduChapter eduChapter : chapterList) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);

            List<VideoVO> videoVOList = new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                if (eduVideo.getChapterId()
                            .equals(eduChapter.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    videoVOList.add(videoVO);
                }
            }

            chapterVO.setChildren(videoVOList);
            chapterVOList.add(chapterVO);
        }
        return chapterVOList;
    }

    /**
     * 按课程 ID 删除
     *
     * @param courseId 课程编号
     */
    @Override
    public void removeByCourseId(String courseId) {
        final LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, courseId);
        this.remove(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        if (eduVideoService.existByChapterId(id)) {
            throw new GuliException(ResultCode.ERROR, "请先删除该章节下的所有课时");
        }
        return super.removeById(id);
    }
}
