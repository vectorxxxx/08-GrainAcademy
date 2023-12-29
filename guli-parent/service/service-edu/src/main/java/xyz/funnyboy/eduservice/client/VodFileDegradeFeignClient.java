package xyz.funnyboy.eduservice.client;

import org.springframework.stereotype.Component;
import xyz.funnyboy.commonutils.R;

import java.util.List;

/**
 * 熔断器实现类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 13:35:17
 */
@Component
public class VodFileDegradeFeignClient implements VodClient
{
    @Override
    public R removeVideo(String videoId) {
        // throw new GuliException(ResultCode.ERROR, "removeVideo time out");
        return R.error()
                .message("removeVideo time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        // throw new GuliException(ResultCode.ERROR, "removeVideoList time out");
        return R.error()
                .message("removeVideoList time out");
    }
}
