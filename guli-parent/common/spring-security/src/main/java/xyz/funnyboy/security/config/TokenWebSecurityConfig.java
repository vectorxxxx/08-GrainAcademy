package xyz.funnyboy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.funnyboy.security.filter.TokenAuthenticationFilter;
import xyz.funnyboy.security.filter.TokenLoginFilter;
import xyz.funnyboy.security.security.DefaultPasswordEncoder;
import xyz.funnyboy.security.security.TokenLogoutHandler;
import xyz.funnyboy.security.security.TokenManager;
import xyz.funnyboy.security.security.UnauthorizedEntryPoint;

import java.util.List;

/**
 * Security配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 20:46:50
 */
@Configuration
// 开启Spring Security的功能
@EnableWebSecurity
// 开启方法上的安全注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter
{

    private final UserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 配置设置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 异常处理
        http.exceptionHandling()
            // 设置未授权时的处理方式
            .authenticationEntryPoint(new UnauthorizedEntryPoint())
            .and()
            // 禁用csrf
            .csrf()
            .disable()
            // 设置所有请求都必须被认证
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            // 设置登出url
            .logout()
            .logoutUrl("/admin/acl/index/logout")
            // 设置登出处理方式
            .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
            .and()
            // 设置登录过滤器
            .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
            // 设置认证过滤器
            .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate))
            // 设置http basic认证
            .httpBasic();
    }

    /**
     * 密码处理
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     *
     * @param web web
     * @throws Exception 例外
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // web.ignoring()
        //    .antMatchers("/api/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        web.ignoring()
           .antMatchers("/*/**");
    }
}
