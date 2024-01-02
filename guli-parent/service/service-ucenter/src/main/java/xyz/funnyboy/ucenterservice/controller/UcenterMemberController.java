package xyz.funnyboy.ucenterservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.vo.UserInfoVO;
import xyz.funnyboy.ucenterservice.entity.UcenterMember;
import xyz.funnyboy.ucenterservice.entity.vo.LoginVO;
import xyz.funnyboy.ucenterservice.entity.vo.RegisterVO;
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
            @ApiParam(name = "registerVO",
                      value = "注册信息",
                      required = true)
            @RequestBody
                    RegisterVO registerVO) {
        memberService.register(registerVO);
        return R.ok();
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(
            @ApiParam(name = "loginVO",
                      value = "登录信息",
                      required = true)
            @RequestBody
                    LoginVO loginVO) {
        final String token = memberService.login(loginVO);
        return R.ok()
                .data("token", token);
    }

    @ApiOperation(value = "会员信息")
    @GetMapping("/info")
    public R info(
            @ApiParam(name = "request",
                      value = "HttpServletRequest",
                      required = true)
                    HttpServletRequest request) {
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UserInfoVO userInfoVO = memberService.getLoginInfo(memberId);
        return R.ok()
                .data("userInfo", userInfoVO);
    }

    @ApiOperation(value = "根据id获取会员信息")
    @GetMapping("/getInfo/{id}")
    public R getInfo(
            @ApiParam(name = "id",
                      value = "会员id",
                      required = true)
            @PathVariable("id")
                    String id) {
        final UcenterMember member = memberService.getById(id);
        return R.ok()
                .data("nickname", member.getNickname())
                .data("avatar", member.getAvatar());
    }

    @ApiOperation(value = "根据id获取会员信息")
    @GetMapping("/getInfo/remote/{id}")
    public UserInfoVO getInfoRemote(
            @ApiParam(name = "id",
                      value = "会员id",
                      required = true)
            @PathVariable("id")
                    String id) {
        final UcenterMember member = memberService.getById(id);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(member, userInfoVO);
        return userInfoVO;
    }

    @ApiOperation(value = "统计某一天的注册人数")
    @GetMapping("/countRegister/remote/{day}")
    public R countRegister(
            @ApiParam(name = "day",
                      value = "日期",
                      required = true)
            @PathVariable("day")
                    String day) {
        final int countRegister = memberService.countRegister(day);
        return R.ok()
                .data("countRegister", countRegister);
    }
}

