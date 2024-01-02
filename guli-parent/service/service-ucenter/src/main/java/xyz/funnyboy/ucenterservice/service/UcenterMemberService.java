package xyz.funnyboy.ucenterservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.commonutils.vo.UserInfoVO;
import xyz.funnyboy.ucenterservice.entity.UcenterMember;
import xyz.funnyboy.ucenterservice.entity.vo.LoginVO;
import xyz.funnyboy.ucenterservice.entity.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2023-12-30
 */
public interface UcenterMemberService extends IService<UcenterMember>
{

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     */
    void register(RegisterVO registerVO);

    /**
     * 登录
     *
     * @param loginVO 登录 VO
     * @return {@link String}
     */
    String login(LoginVO loginVO);

    /**
     * 获取登录信息
     *
     * @param memberId 会员编号
     * @return {@link LoginVO}
     */
    UserInfoVO getLoginInfo(String memberId);

    /**
     * 根据OpenID获取会员信息
     *
     * @param openid openid
     * @return {@link UcenterMember}
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     * 统计某一天的注册人数
     *
     * @param day 日期
     * @return int
     */
    int countRegister(String day);
}
