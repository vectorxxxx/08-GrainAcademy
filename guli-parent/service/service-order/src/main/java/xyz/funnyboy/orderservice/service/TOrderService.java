package xyz.funnyboy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.orderservice.entity.TOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
public interface TOrderService extends IService<TOrder>
{

    /**
     * 创建订单
     *
     * @param courseId 课程编号
     * @param memberId 会员编号
     * @return {@link String}
     */
    String createOrder(String courseId, String memberId);
}
