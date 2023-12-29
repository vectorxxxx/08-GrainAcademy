package xyz.funnyboy.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.eduservice.entity.EduVideo;
import xyz.funnyboy.eduservice.entity.vo.VideoVO;

import java.io.Serializable;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
public interface EduVideoService extends IService<EduVideo>
{

    /**
     * 按章节 ID 获取课时
     *
     * @param id 编号
     * @return boolean
     */
    boolean existByChapterId(Serializable id);

    /**
     * 保存视频 VO
     *
     * @param videoVO 视频VO
     */
    void saveVideoVO(VideoVO videoVO);

    /**
     * 根据 ID获取视频 Vo
     *
     * @param id 编号
     * @return {@link VideoVO}
     */
    VideoVO getVideoVOById(String id);

    /**
     * 更新视频VO
     *
     * @param videoVO 视频配音
     */
    void updateVideoVO(VideoVO videoVO);

    /**
     * 按课程 ID 删除
     *
     * @param courseId 课程编号
     */
    void removeByCourseId(String courseId);

    /**
     * 按 ID 移除视频
     *
     * @param id 编号
     */
    void removeVideoById(String id);
}
