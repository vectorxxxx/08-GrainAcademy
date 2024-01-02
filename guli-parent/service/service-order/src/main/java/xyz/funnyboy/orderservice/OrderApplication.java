package xyz.funnyboy.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Order启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-01 23:35:51
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务发现注册
@EnableDiscoveryClient
// 开启Feign客户端
@EnableFeignClients
public class OrderApplication
{
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
