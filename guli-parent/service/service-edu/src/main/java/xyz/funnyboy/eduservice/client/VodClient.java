package xyz.funnyboy.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.funnyboy.commonutils.R;

import java.util.List;

/**
 * 视频点播客户端
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 10:53:00
 */
@FeignClient(name = "service-vod",
             fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient
{
    @DeleteMapping("{videoId}")
    R removeVideo(
            @PathVariable("videoId")
                    String videoId);

    @DeleteMapping("")
    R removeVideoList(
            @RequestParam("videoIdList")
                    List<String> videoIdList);
}
