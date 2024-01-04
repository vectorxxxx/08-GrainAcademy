package xyz.funnyboy.ucenterservice.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.ResultCode;
import xyz.funnyboy.servicebase.exception.GuliException;
import xyz.funnyboy.ucenterservice.entity.UcenterMember;
import xyz.funnyboy.ucenterservice.service.UcenterMemberService;
import xyz.funnyboy.ucenterservice.utils.ConstantPropertiesUtil;
import xyz.funnyboy.ucenterservice.utils.HttpClientUtils;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信API Controller
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 22:11:04
 */
@Api(description = "微信API")
@Controller
@RequestMapping("/api/ucenter/wx")
// @CrossOrigin
@Slf4j
public class WxApiController
{
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "微信登录")
    @GetMapping("/login")
    public String getOrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append("https://open.weixin.qq.com/connect/qrconnect")
               .append("?appid=%s")
               .append("&redirect_uri=%s")
               .append("&response_type=code")
               .append("&scope=snsapi_login")
               .append("&state=%s")
               .append("#wechat_redirect");

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击），一般情况下会使用一个随机数
        String state = UUID.randomUUID()
                           .toString()
                           .replaceAll("-", "");

        //设置%s里面值
        String url = String.format(baseUrl.toString(), ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, state);

        return "redirect:" + url;
    }

    //2 获取扫描人信息，添加数据
    @ApiOperation(value = "微信登录回调")
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            //1 获取code值，临时票据，类似于验证码
            //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            StringBuilder baseAccessTokenUrl = new StringBuilder();
            baseAccessTokenUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                              .append("?appid=%s")
                              .append("&secret=%s")
                              .append("&code=%s")
                              .append("&grant_type=authorization_code");
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(baseAccessTokenUrl.toString(), ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);

            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            final Gson gson = new Gson();
            final Map tokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            final String accessToken = (String) tokenMap.get("access_token");
            final String openid = (String) tokenMap.get("openid");

            //把扫描人信息添加数据库里面
            //判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            //memeber是空，表没有相同微信数据，进行添加
            if (member == null) {
                member = saveMemberInfo(accessToken, openid);
            }

            //使用jwt根据member对象生成token字符串
            final String token = JwtUtils.createToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + token;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 保存会员信息
     *
     * @param accessToken 访问令牌
     * @param openid      openid
     * @throws Exception 异常
     */
    private UcenterMember saveMemberInfo(String accessToken, String openid) throws Exception {
        UcenterMember member;
        //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
        //访问微信的资源服务器，获取用户信息
        StringBuilder baseUserInfoUrl = new StringBuilder();
        baseUserInfoUrl.append("https://api.weixin.qq.com/sns/userinfo")
                       .append("?access_token=%s")
                       .append("&openid=%s");
        //拼接两个参数
        String userInfoUrl = String.format(baseUserInfoUrl.toString(), accessToken, openid);

        //发送请求
        String userInfo = HttpClientUtils.get(userInfoUrl);

        //获取返回userinfo字符串扫描人信息
        Gson gson = new Gson();
        Map userInfoMap = gson.fromJson(userInfo, HashMap.class);
        String nickName = (String) userInfoMap.get("nickname");
        String headImgUrl = (String) userInfoMap.get("headimgurl");

        // 保存会员信息
        member = new UcenterMember();
        member.setOpenid(openid);
        member.setNickname(nickName);
        member.setAvatar(headImgUrl);
        memberService.save(member);

        return member;
    }
}
