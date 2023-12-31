package xyz.funnyboy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 22:43:51
 */
public interface VodService
{
    /**
     * 上传视频
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频
     *
     * @param videoId 视频ID
     * @return {@code true} 删除成功
     */
    void removeVideo(String videoId);

    /**
     * 删除视频列表
     *
     * @param videoIdList 视频 ID 列表
     */
    void removeVideoList(List<String> videoIdList);

    /**
     * 获取播放凭证
     *
     * @param videoId 视频 ID
     * @return {@link String}
     */
    String getPlayAuth(String videoId) throws Exception;
}
