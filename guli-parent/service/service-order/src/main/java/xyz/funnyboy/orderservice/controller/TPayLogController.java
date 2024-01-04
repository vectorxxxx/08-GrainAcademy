package xyz.funnyboy.orderservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.orderservice.service.TPayLogService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
@Api(description = "支付管理")
// @CrossOrigin
@RestController
@RequestMapping("/orderservice/paylog")
public class TPayLogController
{
    @Autowired
    private TPayLogService payLogService;

    @ApiOperation(value = "创建支付日志")
    @PostMapping("createNative/{orderNo}")
    public R createNative(
            @ApiParam(name = "orderNo",
                      value = "订单号",
                      required = true)
            @PathVariable("orderNo")
                    String orderNo) {
        // 返回信息，包含二维码地址，还有其他需要的信息
        Map<String, Object> map = payLogService.createNative(orderNo);
        return R.ok()
                .data(map);
    }

    @ApiOperation(value = "查询支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(
            @ApiParam(name = "orderNo",
                      value = "订单号",
                      required = true)
            @PathVariable("orderNo")
                    String orderNo) {
        // 返回信息，包含二维码地址，还有其他需要的信息
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null || map.isEmpty()) {
            return R.ok()
                    .message("支付出错");
        }
        // 支付成功
        if ("SUCCESS".equals(map.get("trade_state"))) {
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok()
                    .message("支付成功");
        }
        return R.ok()
                .code(25000)
                .message("支付中");
    }
}

