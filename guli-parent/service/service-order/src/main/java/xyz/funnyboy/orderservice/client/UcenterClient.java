package xyz.funnyboy.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.vo.UserInfoVO;

/**
 * 课程远程接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-01 23:34:58
 */
@FeignClient(name = "service-ucenter",
             fallback = UcenterClientFallback.class)
@Component
public interface UcenterClient
{
    @GetMapping("/educenter/member/getInfo/remote/{id}")
    UserInfoVO getInfoRemote(
            @PathVariable("id")
                    String id);
}
