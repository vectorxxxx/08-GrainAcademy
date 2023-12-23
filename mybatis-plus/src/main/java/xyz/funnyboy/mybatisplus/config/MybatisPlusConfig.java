package xyz.funnyboy.mybatisplus.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 注册配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-22
 */
@EnableTransactionManagement
@Configuration
@MapperScan("xyz.funnyboy.mybatisplus.mapper")
public class MybatisPlusConfig
{

    /**
     * 乐观锁插件
     *
     * @return {@link OptimisticLockerInterceptor}
     * @see {@linktourl <a href="https://baomidou.com/pages/0d93c0/">乐观锁插件</a>}
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页插件
     *
     * @return {@link PaginationInterceptor}
     * @see {@linktourl <a href="https://baomidou.com/pages/97710a/">分页插件</a>}
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 逻辑删除
     *
     * @return {@link ISqlInjector}
     * @see {@linktourl <a href="https://baomidou.com/pages/6b03c5/">逻辑删除</a>}
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * SQL 执行性能分析插件
     * <p>
     * 开发环境使用，线上不推荐。
     * <p>
     *
     * @return {@link PerformanceInterceptor}
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 参数：maxTime：SQL 执行最大时长，超过自动停止运行，有助于发现问题。
        // performanceInterceptor.setMaxTime(100);
        performanceInterceptor.setMaxTime(5);
        // 参数：format：SQL是否格式化，默认false。
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }
}
