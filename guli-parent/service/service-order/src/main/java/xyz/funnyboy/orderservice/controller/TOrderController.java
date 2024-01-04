package xyz.funnyboy.orderservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.orderservice.entity.TOrder;
import xyz.funnyboy.orderservice.service.TOrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
@Api(description = "订单管理")
// @CrossOrigin
@RestController
@RequestMapping("/orderservice/order")
public class TOrderController
{
    @Autowired
    private TOrderService orderService;

    @ApiOperation(value = "创建订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(
            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @PathVariable("courseId")
                    String courseId, HttpServletRequest request) {
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        final String orderNo = orderService.createOrder(courseId, memberId);
        return R.ok()
                .data("orderNo", orderNo);
    }

    @ApiOperation(value = "获取订单")
    @GetMapping("getOrder/{orderNo}")
    public R getOrder(
            @ApiParam(name = "orderNo",
                      value = "订单编号",
                      required = true)
            @PathVariable("orderNo")
                    String orderNo) {
        final LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, orderNo);
        final TOrder order = orderService.getOne(queryWrapper);
        return R.ok()
                .data("item", order);
    }

    @ApiOperation(value = "是否购买过该课程")
    @GetMapping("isBuyCourse/remote/{memberId}/{courseId}")
    boolean isBuyCourse(
            @ApiParam(name = "memberId",
                      value = "会员ID",
                      required = true)
            @PathVariable("memberId")
                    String memberId,

            @ApiParam(name = "courseId",
                      value = "课程ID",
                      required = true)
            @PathVariable("courseId")
                    String courseId) {
        final LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<TOrder>().eq(TOrder::getMemberId, memberId)
                                                                                        .eq(TOrder::getCourseId, courseId)
                                                                                        .eq(TOrder::getStatus, 1);
        final int count = orderService.count(queryWrapper);
        return count > 0;
    }
}

