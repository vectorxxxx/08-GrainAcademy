package xyz.funnyboy.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;
import xyz.funnyboy.commonutils.vo.UserInfoVO;
import xyz.funnyboy.orderservice.client.CourseClient;
import xyz.funnyboy.orderservice.client.UcenterClient;
import xyz.funnyboy.orderservice.entity.TOrder;
import xyz.funnyboy.orderservice.mapper.TOrderMapper;
import xyz.funnyboy.orderservice.service.TOrderService;
import xyz.funnyboy.orderservice.utils.OrderNoUtil;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-01
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService
{
    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 创建订单
     *
     * @param courseId 课程编号
     * @param memberId 会员编号
     * @return {@link String}
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        // 课程信息
        final CoursePublishVO courseInfo = courseClient.getCourseInfoRemote(courseId);
        // 会员信息
        final UserInfoVO memberInfo = ucenterClient.getInfoRemote(memberId);

        if (courseInfo == null || memberInfo == null) {
            return "";
        }

        // 订单信息
        final TOrder order = new TOrder();
        // 课程信息
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        // 会员信息
        order.setMemberId(memberId);
        order.setNickname(memberInfo.getNickname());
        order.setMobile(memberInfo.getMobile());
        // 订单编号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        // "订单金额（分）"
        order.setTotalFee(BigDecimal.valueOf(Double.parseDouble(courseInfo.getPrice())));
        // "支付类型（1：微信 2：支付宝）"
        order.setPayType(1);
        // "订单状态（0：未支付 1：已支付）"
        order.setStatus(0);
        this.save(order);
        return order.getOrderNo();
    }
}
