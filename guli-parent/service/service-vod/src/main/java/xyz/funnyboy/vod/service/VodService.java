package xyz.funnyboy.vod.service;

import org.springframework.web.multipart.MultipartFile;

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
     * 删除文件
     *
     * @param videoId 视频ID
     * @return {@code true} 删除成功
     */
    void removeVideo(String videoId);
}
