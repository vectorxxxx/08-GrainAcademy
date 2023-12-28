package xyz.funnyboy.vod.utils;

import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyun.vod20170321.Client;
import com.aliyun.vod20170321.models.*;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Vod Sdk 测试
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 19:49:36
 */
public class VodSdkTest
{

    private static final String ACCESS_KEY_ID = "LTAI5tLJnjE7YF12eK3oSAz3";
    private static final String ACCESS_KEY_SECRET = "Ci1D7zZ8nCdvP5ZATe2eHwL8xeHxTH";
    private static final String VIDEO_ID = "a0ceed71a56e71eebfd05420848d0102";

    public Client createClient() throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(ACCESS_KEY_ID)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(ACCESS_KEY_SECRET);
        // Endpoint 请参考 https://api.aliyun.com/product/vod
        config.endpoint = "vod.cn-shanghai.aliyuncs.com";
        return new Client(config);
    }

    @Test
    public void testGetVideoPlayAuth() throws Exception {
        final Client client = createClient();
        final GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(VIDEO_ID);
        final GetVideoPlayAuthResponse response = client.getVideoPlayAuthWithOptions(request, new RuntimeOptions());
        // 输出结果
        System.out.println(Common.toJSONString(response));
        final GetVideoPlayAuthResponseBody responseBody = response.getBody();
        System.out.println(responseBody.getPlayAuth());
        System.out.println(responseBody.getVideoMeta()
                                       .getTitle());
    }

    @Test
    public void testGetPlayInfo() throws Exception {
        final Client client = createClient();
        final GetPlayInfoRequest request = new GetPlayInfoRequest().setVideoId(VIDEO_ID);
        final GetPlayInfoResponse response = client.getPlayInfoWithOptions(request, new RuntimeOptions());
        // 输出结果
        System.out.println(Common.toJSONString(response));
        final GetPlayInfoResponseBody responseBody = response.getBody();
        final List<GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo> playInfoList = responseBody.getPlayInfoList()
                                                                                                                   .getPlayInfo();
        for (GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo info : playInfoList) {
            System.out.println(info.getPlayURL());
        }
        System.out.println(responseBody.getVideoBase()
                                       .getTitle());
    }

    @Test
    public void testUploadVideo() {
        final String title = "Test111";
        final String fileName = "D:/workspace-mine/08-GrainAcademy/guli-parent/service/service-vod/src/main/resources/video/test.mp4";
        final UploadVideoRequest request = new UploadVideoRequest(ACCESS_KEY_ID, ACCESS_KEY_SECRET, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);

        final UploadVideoImpl uploader = new UploadVideoImpl();
        final UploadVideoResponse response = uploader.uploadVideo(request);

        // 返回结果
        System.out.println("RequestId=" + response.getRequestId());  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId());
        }
        else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.println("VideoId=" + response.getVideoId());
            System.out.println("ErrorCode=" + response.getCode());
            System.out.println("ErrorMessage=" + response.getMessage());
        }
    }

    @Test
    public void testUploadVideoStream() throws FileNotFoundException {
        final String title = "Test222";
        final String fileName = "test2.mp4";
        final InputStream content = new BufferedInputStream(
                new FileInputStream("D:/workspace-mine/08-GrainAcademy/guli-parent/service/service-vod/src/main/resources/video/test.mp4"));
        final UploadStreamRequest request = new UploadStreamRequest(ACCESS_KEY_ID, ACCESS_KEY_SECRET, title, fileName, content);

        final UploadVideoImpl uploader = new UploadVideoImpl();
        final UploadStreamResponse response = uploader.uploadStream(request);

        // 返回结果
        System.out.println("RequestId=" + response.getRequestId());  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId());
        }
        else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.println("VideoId=" + response.getVideoId());
            System.out.println("ErrorCode=" + response.getCode());
            System.out.println("ErrorMessage=" + response.getMessage());
        }
    }
}
