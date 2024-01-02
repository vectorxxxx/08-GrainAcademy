package xyz.funnyboy.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.orderservice.entity.TOrder;
import xyz.funnyboy.orderservice.entity.TPayLog;
import xyz.funnyboy.orderservice.mapper.TPayLogMapper;
import xyz.funnyboy.orderservice.service.TOrderService;
import xyz.funnyboy.orderservice.service.TPayLogService;
import xyz.funnyboy.orderservice.utils.ConstantPropertiesUtil;
import xyz.funnyboy.orderservice.utils.HttpClient;
import xyz.funnyboy.servicebase.exception.GuliException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
@Service
@Slf4j
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService
{
    @Autowired
    private TOrderService orderService;

    /**
     * 生成微信支付二维码接口
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            //1 根据订单号查询订单信息
            final LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, orderNo);
            final TOrder order = orderService.getOne(queryWrapper);

            //2 使用map设置生成二维码需要参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", ConstantPropertiesUtil.APPID);
            m.put("mch_id", ConstantPropertiesUtil.MCH_ID);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee()
                                    .multiply(new BigDecimal("100"))
                                    .longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", ConstantPropertiesUtil.NOTIFY_URL);
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient(ConstantPropertiesUtil.WX_PAY_URL + "/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, ConstantPropertiesUtil.PRIVATE_KEY));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            map.forEach((key, value) -> log.info(key + " : " + value));
            return map;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GuliException(20001, "生成二维码失败：" + e.getMessage());
        }
    }

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            // 封装参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", ConstantPropertiesUtil.APPID);
            m.put("mch_id", ConstantPropertiesUtil.MCH_ID);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            // 发送httpclient
            HttpClient client = new HttpClient(ConstantPropertiesUtil.WX_PAY_URL + "/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, ConstantPropertiesUtil.PRIVATE_KEY));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            // 转成Map再返回
            resultMap.forEach((key, value) -> log.info(key + " : " + value));
            return resultMap;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新订单状态
     *
     * @param map 集合
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        final String tradeNo = map.get("out_trade_no");
        // 查询订单信息
        final LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, tradeNo);
        final TOrder order = orderService.getOne(queryWrapper);

        // 更新订单状态
        if (order.getStatus() == 1) {
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        // 添加支付记录
        final TPayLog payLog = new TPayLog();
        // 订单号
        payLog.setOrderNo(tradeNo);
        // 支付时间
        payLog.setPayTime(new Date());
        // 支付方式 1微信
        payLog.setPayType(1);
        // 订单金额(分)
        payLog.setTotalFee(order.getTotalFee());
        // 流水号
        payLog.setTransactionId(map.get("transaction_id"));
        // 支付状态
        payLog.setTradeState(map.get("trade_state"));
        payLog.setAttr(JSONObject.toJSONString(map));

        this.save(payLog);
    }
}
