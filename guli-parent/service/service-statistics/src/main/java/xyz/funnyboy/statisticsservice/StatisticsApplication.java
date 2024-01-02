package xyz.funnyboy.statisticsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 统计分析启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 19:47:52
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现
@EnableDiscoveryClient
// 开启feign客户端
@EnableFeignClients
// 开启定时任务
@EnableScheduling
public class StatisticsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
