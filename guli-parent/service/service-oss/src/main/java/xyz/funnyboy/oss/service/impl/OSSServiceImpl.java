package xyz.funnyboy.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.oss.service.OSSService;
import xyz.funnyboy.oss.utils.ConstantPropertiesUtil;
import xyz.funnyboy.servicebase.exception.GuliException;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

/**
 * OSSService实现类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:30:31
 */
@Service
@Slf4j
public class OSSServiceImpl implements OSSService
{
    /**
     * 上传头像
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadAvatar(MultipartFile file) {
        // 获取 OSS 配置信息
        final String endPoint = ConstantPropertiesUtil.END_POINT;
        final String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        final String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        final String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        final String protocol = ConstantPropertiesUtil.PROTOCOL;

        OSS ossClient = null;
        try {
            // OSS 客户端实例
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            // 源文件名
            final String originalFilename = file.getOriginalFilename();
            final String originalFileType = Objects.requireNonNull(originalFilename)
                                                   .substring(originalFilename.lastIndexOf("."));

            // 目标文件
            final String filePath = new DateTime().toString("yyyy-MM-dd");
            final String fileName = UUID.randomUUID() + originalFileType;
            final String fileUrl = filePath + "/" + fileName;
            final InputStream content = file.getInputStream();

            // 上传文件
            ossClient.putObject(bucketName, fileUrl, content);

            // 返回文件地址
            return protocol + "://" + bucketName + "." + endPoint + "/" + fileUrl;
        }
        catch (Exception e) {
            log.error("上传头像失败：" + e.getMessage(), e);
            throw new GuliException(ResultCode.UPLOAD_AVATAR_ERROR, e.getMessage());
        }
        finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
