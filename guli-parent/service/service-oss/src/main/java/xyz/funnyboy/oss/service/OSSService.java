package xyz.funnyboy.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSSService
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:29:17
 */
public interface OSSService
{
    /**
     * 上传头像
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadAvatar(MultipartFile file);
}
