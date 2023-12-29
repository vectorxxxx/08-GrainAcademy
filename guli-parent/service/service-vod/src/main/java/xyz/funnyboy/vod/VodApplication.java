package xyz.funnyboy.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Vod启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 21:47:28
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy",
                       exclude = DataSourceAutoConfiguration.class)
// 启动服务注册发现（Nacos）
@EnableDiscoveryClient
public class VodApplication
{
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
