package xyz.funnyboy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.eduservice.client.VodClient;
import xyz.funnyboy.eduservice.entity.EduVideo;
import xyz.funnyboy.eduservice.entity.vo.VideoVO;
import xyz.funnyboy.eduservice.mapper.EduVideoMapper;
import xyz.funnyboy.eduservice.service.EduVideoService;
import xyz.funnyboy.servicebase.exception.GuliException;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService
{
    @Autowired
    private VodClient vodClient;

    /**
     * 按章节 ID 获取课时
     *
     * @param id 编号
     * @return boolean
     */
    @Override
    public boolean existByChapterId(Serializable id) {
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId, id);
        return this.count(queryWrapper) > 0;
    }

    /**
     * 保存视频 VO
     *
     * @param videoVO 视频VO
     */
    @Override
    public void saveVideoVO(VideoVO videoVO) {
        final EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoVO, eduVideo);
        final boolean save = this.save(eduVideo);
        if (!save) {
            throw new GuliException(ResultCode.ERROR, "课时信息保存失败");
        }
    }

    /**
     * 根据 ID获取视频 Vo
     *
     * @param id 编号
     * @return {@link VideoVO}
     */
    @Override
    public VideoVO getVideoVOById(String id) {
        final EduVideo eduVideo = this.getById(id);
        VideoVO videoVO = new VideoVO();
        BeanUtils.copyProperties(eduVideo, videoVO);
        return videoVO;
    }

    /**
     * 更新视频VO
     *
     * @param videoVO 视频配音
     */
    @Override
    public void updateVideoVO(VideoVO videoVO) {
        final EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoVO, eduVideo);
        final boolean update = this.updateById(eduVideo);
        if (!update) {
            throw new GuliException(ResultCode.ERROR, "课时信息更新失败");
        }
    }

    /**
     * 按课程 ID 删除
     *
     * @param courseId 课程编号
     */
    @Override
    public void removeByCourseId(String courseId) {
        // 查询所有视频列表
        final LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId, courseId);
        final List<EduVideo> videoList = this.list(queryWrapper);

        // 删除云端视频
        final List<String> videoIdList = videoList.stream()
                                                  .map(EduVideo::getVideoSourceId)
                                                  .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(videoIdList)) {
            final R ret = vodClient.removeVideoList(videoIdList);
            // if (ResultCode.ERROR.equals(ret.getCode())) {
            //     throw new GuliException(ret.getCode(), ret.getMessage());
            // }
        }

        // 删除库中记录
        this.remove(queryWrapper);
    }

    /**
     * 按 ID 移除视频
     *
     * @param id 编号
     */
    @Override
    public void removeVideoById(String id) {
        // 删除云端视频
        final EduVideo eduVideo = this.getById(id);
        final String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }

        // 删除库中记录
        this.removeById(id);
    }
}
