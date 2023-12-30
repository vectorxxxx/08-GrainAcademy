package xyz.funnyboy.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.MD5;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.servicebase.exception.GuliException;
import xyz.funnyboy.ucenterservice.entity.UcenterMember;
import xyz.funnyboy.ucenterservice.entity.vo.LoginVO;
import xyz.funnyboy.ucenterservice.entity.vo.RegisterVO;
import xyz.funnyboy.ucenterservice.entity.vo.UserInfoVO;
import xyz.funnyboy.ucenterservice.mapper.UcenterMemberMapper;
import xyz.funnyboy.ucenterservice.service.UcenterMemberService;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2023-12-30
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService
{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     */
    @Override
    public void register(RegisterVO registerVO) {
        final String nicknme = registerVO.getNicknme();
        if (StringUtils.isEmpty(nicknme)) {
            throw new GuliException(ResultCode.ERROR, "昵称不能为空");
        }

        final String mobile = registerVO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new GuliException(ResultCode.ERROR, "手机号不能为空");
        }

        final String password = registerVO.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCode.ERROR, "密码不能为空");
        }

        final String code = registerVO.getCode();
        if (StringUtils.isEmpty(code)) {
            throw new GuliException(ResultCode.ERROR, "验证码不能为空");
        }

        final String realCode = redisTemplate.opsForValue()
                                             .get(mobile);
        if (!code.equals(realCode)) {
            throw new GuliException(ResultCode.ERROR, "验证码错误");
        }

        final int count = this.count(new LambdaQueryWrapper<UcenterMember>().eq(UcenterMember::getMobile, mobile));
        if (count > 0) {
            throw new GuliException(ResultCode.ERROR, "手机号已存在");
        }

        // 保存用户
        final UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(registerVO, member);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        this.save(member);
    }

    /**
     * 登录
     *
     * @param loginVO 登录 VO
     * @return {@link String}
     */
    @Override
    public String login(LoginVO loginVO) {
        final String mobile = loginVO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new GuliException(ResultCode.ERROR, "手机号不能为空");
        }

        final String password = loginVO.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCode.ERROR, "密码不能为空");
        }

        final UcenterMember member = this.getOne(new LambdaQueryWrapper<UcenterMember>().eq(UcenterMember::getMobile, mobile));
        if (member == null) {
            throw new GuliException(ResultCode.ERROR, "手机号不存在");
        }

        if (!MD5.encrypt(password)
                .equals(member.getPassword())) {
            throw new GuliException(ResultCode.ERROR, "密码错误");
        }

        if (member.getIsDisabled()) {
            throw new GuliException(ResultCode.ERROR, "账号已被禁用");
        }

        return JwtUtils.createToken(member.getId(), member.getNickname());
    }

    /**
     * 获取登录信息
     *
     * @param memberId 会员编号
     * @return {@link LoginVO}
     */
    @Override
    public UserInfoVO getLoginInfo(String memberId) {
        final UcenterMember member = this.getById(memberId);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(member, userInfoVO);
        return userInfoVO;
    }
}
