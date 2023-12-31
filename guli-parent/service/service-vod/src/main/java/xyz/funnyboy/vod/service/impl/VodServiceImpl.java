package xyz.funnyboy.vod.service.impl;

import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod20170321.Client;
import com.aliyun.vod20170321.models.DeleteVideoRequest;
import com.aliyun.vod20170321.models.DeleteVideoResponse;
import com.aliyun.vod20170321.models.GetVideoPlayAuthRequest;
import com.aliyun.vod20170321.models.GetVideoPlayAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.servicebase.exception.GuliException;
import xyz.funnyboy.vod.service.VodService;
import xyz.funnyboy.vod.utils.AliyunVodSDKUtils;
import xyz.funnyboy.vod.utils.ConstantPropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 视频Service实现类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 22:44:30
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService
{
    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            // 文件信息
            final String originalFilename = file.getOriginalFilename();
            final String title = Objects.requireNonNull(originalFilename)
                                        .substring(0, originalFilename.lastIndexOf("."));
            final InputStream content = file.getInputStream();
            // 上传文件流
            final UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, originalFilename,
                    content);
            final UploadVideoImpl uploader = new UploadVideoImpl();
            final UploadStreamResponse response = uploader.uploadStream(request);

            // 返回结果
            final String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                final String message = "上传文件流失败，code:" + response.getCode() + ", message:" + response.getMessage();
                log.warn(message);
                if (StringUtils.isEmpty(videoId)) {
                    throw new GuliException(ResultCode.ERROR, "视频点播服务上传文件失败");
                }
            }
            return videoId;
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GuliException(ResultCode.ERROR, "视频点播服务上传文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param videoId 视频ID
     * @return {@code true} 删除成功
     */
    @Override
    public void removeVideo(String videoId) {
        try {
            final Client client = AliyunVodSDKUtils.createClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            final DeleteVideoRequest request = new DeleteVideoRequest().setVideoIds(videoId);
            final DeleteVideoResponse response = client.deleteVideo(request);
            log.info("RequestId = " + response.getBody()
                                              .getRequestId());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GuliException(ResultCode.ERROR, "视频删除失败");
        }

    }

    /**
     * 删除视频列表
     *
     * @param videoIdList 视频 ID 列表
     */
    @Override
    public void removeVideoList(List<String> videoIdList) {
        final String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
        removeVideo(videoIds);
    }

    /**
     * 获取播放凭证
     *
     * @param videoId 视频 ID
     * @return {@link String}
     */
    @Override
    public String getPlayAuth(String videoId) throws Exception {
        final Client client = AliyunVodSDKUtils.createClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        final GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        final GetVideoPlayAuthResponse response = client.getVideoPlayAuthWithOptions(request, new RuntimeOptions());
        return response.getBody()
                       .getPlayAuth();
    }
}
