package xyz.funnyboy.security.filter;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.commonutils.ResponseUtil;
import xyz.funnyboy.security.security.TokenManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 令牌身份验证筛选器
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/03
 * @see BasicAuthenticationFilter
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter
{
    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("=================" + req.getRequestURI());
        // 判断是否管理员
        if (!req.getRequestURI()
                .contains("admin")) {
            chain.doFilter(req, res);
            return;
        }

        // 获取认证信息
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        }
        catch (Exception e) {
            ResponseUtil.out(res, R.error());
        }

        // 将认证信息设置到安全上下文中
        if (authentication != null) {
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
        }
        else {
            ResponseUtil.out(res, R.error());
        }

        // 继续执行过滤器链
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        final String token = request.getHeader("token");
        if (token == null || "".equals(token.trim())) {
            return null;
        }

        // 获取用户名
        final String userName = tokenManager.getUserFromToken(token);
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        // 从redis中获取权限值列表
        @SuppressWarnings("unchecked")
        final List<String> permissionValueList = (List<String>) redisTemplate.opsForValue()
                                                                             .get(userName);

        // 创建授权列表
        final Collection<GrantedAuthority> authorities = Objects.requireNonNull(permissionValueList)
                                                                .stream()
                                                                .filter(permissionValue -> !StringUtils.isEmpty(permissionValue))
                                                                .map(SimpleGrantedAuthority::new)
                                                                .collect(Collectors.toList());

        // 如果用户名不为空，则创建用户名密码认证令牌
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }
}
