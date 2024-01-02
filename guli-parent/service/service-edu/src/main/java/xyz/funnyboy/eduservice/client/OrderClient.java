package xyz.funnyboy.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 11:52:32
 */
@Component
@FeignClient(name = "service-order",
             fallback = OrderClientFallback.class)
public interface OrderClient
{
    @GetMapping("/orderservice/order/isBuyCourse/remote/{memberId}/{courseId}")
    boolean isBuyCourse(
            @PathVariable("memberId")
                    String memberId,

            @PathVariable("courseId")
                    String courseId);
}
