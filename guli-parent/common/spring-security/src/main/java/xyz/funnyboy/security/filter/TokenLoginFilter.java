package xyz.funnyboy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.HttpMethod;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.ResponseUtil;
import xyz.funnyboy.security.entity.SecurityUser;
import xyz.funnyboy.security.entity.User;
import xyz.funnyboy.security.security.TokenManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/03
 * @see UsernamePasswordAuthenticationFilter
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter
{

    private static final String ADMIN_ACL_LOGIN = "/admin/acl/login";
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(ADMIN_ACL_LOGIN, HttpMethod.POST.name()));
    }

    /**
     * 尝试身份验证
     *
     * @param req 请求
     * @param res 响应
     * @return {@link Authentication}
     * @throws AuthenticationException 身份验证异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            // 将请求体中的输入流转换为User对象
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

            // 创建一个UsernamePasswordAuthenticationToken对象，用于验证用户
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());

            // 使用认证管理器验证用户
            return authenticationManager.authenticate(authenticationToken);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 身份验证成功
     *
     * @param req   请求
     * @param res   响应
     * @param chain 链
     * @param auth  认证
     * @throws IOException      ioexception
     * @throws ServletException Servlet 异常
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        // 获取认证信息
        final SecurityUser user = (SecurityUser) auth.getPrincipal();

        // 获取当前用户信息
        final User userInfo = user.getCurrentUserInfo();

        // 创建token
        final String token = tokenManager.createToken(userInfo.getUsername());

        // 将用户信息和权限值列表存储到redis中
        redisTemplate.opsForValue()
                     .set(userInfo.getUsername(), user.getPermissionValueList());

        // 返回token
        ResponseUtil.out(res, R.ok()
                               .data("token", token));
    }

    /**
     * 身份验证不成功
     *
     * @param request  请求
     * @param response 响应
     * @param e        e
     * @throws IOException      ioexception
     * @throws ServletException Servlet 异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}
