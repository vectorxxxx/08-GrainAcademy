package xyz.funnyboy.servicebase.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author VectorX
 * @version V1.0
 * @description MyBatisPlus配置类
 * @date 23/12/2023
 */
@Configuration
@EnableTransactionManagement
@MapperScan("xyz.funnyboy.serviceedu.mapper")
public class MyBatisPlusConfig
{
    /**
     * SQL 执行性能分析插件
     * <p>
     * 开发环境使用，线上不推荐。
     *
     * @return {@link PerformanceInterceptor}
     */
    @Bean
    // 设置 dev test 环境开启
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        final PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // maxTime 指的是 sql 最大执行时长
        performanceInterceptor.setMaxTime(1000);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * SQL 注入器
     *
     * @return {@link ISqlInjector}
     * @see {linktourl <a href="https://baomidou.com/pages/6b03c5/">逻辑删除</a>}
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     *
     * @return {@link PaginationInterceptor}
     * @see {linktourl <a href="https://baomidou.com/pages/97710a/">分页插件</a>}
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
