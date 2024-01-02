package xyz.funnyboy.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.R;

/**
 * 会员远程客户端
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 20:27:20
 */
@Component
@FeignClient(name = "service-ucenter",
             fallback = UcenterClientFallback.class)
public interface UcenterClient
{
    @GetMapping("/educenter/member/countRegister/remote/{day}")
    R countRegister(
            @PathVariable("day")
                    String day);
}
