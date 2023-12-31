package xyz.funnyboy.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.R;

/**
 * Ucenter客户端
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-31 16:52:09
 */
@FeignClient(name = "service-ucenter",
             fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient
{
    @GetMapping("/educenter/member/getInfo/{id}")
    R getInfo(
            @PathVariable("id")
                    String id);
}
