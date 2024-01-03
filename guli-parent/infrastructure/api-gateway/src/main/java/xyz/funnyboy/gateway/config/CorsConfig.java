package xyz.funnyboy.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Cors配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 15:42:58
 */
@Configuration
public class CorsConfig
{
    @Bean
    public CorsWebFilter corsWebFilter() {
        // CORS（Cross-Origin Resource Sharing，跨域资源共享）
        CorsConfiguration config = new CorsConfiguration();
        // 添加允许的域名
        config.addAllowedOrigin("*");
        // 添加允许的头部
        config.addAllowedHeader("*");
        // 添加允许的方法
        config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 注册CorsConfiguration
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
