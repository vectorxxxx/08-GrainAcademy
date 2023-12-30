package xyz.funnyboy.ucenterservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.ucenterservice.entity.vo.LoginVO;
import xyz.funnyboy.ucenterservice.entity.vo.RegisterVO;
import xyz.funnyboy.ucenterservice.entity.vo.UserInfoVO;
import xyz.funnyboy.ucenterservice.service.UcenterMemberService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2023-12-30
 */
@Api(description = "会员服务")
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController
{
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(
            @RequestBody
                    RegisterVO registerVO) {
        memberService.register(registerVO);
        return R.ok();
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(
            @RequestBody
                    LoginVO loginVO) {
        final String token = memberService.login(loginVO);
        return R.ok()
                .data("token", token);
    }

    @ApiOperation(value = "会员信息")
    @GetMapping("/info")
    public R info(HttpServletRequest request) {
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UserInfoVO userInfoVO = memberService.getLoginInfo(memberId);
        return R.ok()
                .data("userInfo", userInfoVO);
    }
}

