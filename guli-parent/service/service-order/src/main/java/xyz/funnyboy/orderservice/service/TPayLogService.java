package xyz.funnyboy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.orderservice.entity.TPayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
public interface TPayLogService extends IService<TPayLog>
{

    /**
     * 生成微信支付二维码接口
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, Object> createNative(String orderNo);

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     *
     * @param map 集合
     */
    void updateOrderStatus(Map<String, String> map);
}
