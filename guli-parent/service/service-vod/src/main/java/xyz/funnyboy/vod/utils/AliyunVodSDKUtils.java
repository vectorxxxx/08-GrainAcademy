package xyz.funnyboy.vod.utils;

import com.aliyun.teaopenapi.models.Config;
import com.aliyun.vod20170321.Client;

/**
 * 视频点播服务 SDK 工具类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 21:49:22
 */
public class AliyunVodSDKUtils
{
    /**
     * 创建客户端
     *
     * @param accessKeyId     访问密钥 ID
     * @param accessKeySecret 访问密钥密钥
     * @return {@link Client}
     * @throws Exception 异常
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/vod
        config.endpoint = "vod.cn-shanghai.aliyuncs.com";
        return new Client(config);
    }
}
