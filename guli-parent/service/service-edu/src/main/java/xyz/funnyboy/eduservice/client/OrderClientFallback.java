package xyz.funnyboy.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * 订单接口错误回调
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 11:53:15
 */
@Component
public class OrderClientFallback implements OrderClient
{
    @Override
    public boolean isBuyCourse(String memberId, String courseId) {
        return false;
    }
}
