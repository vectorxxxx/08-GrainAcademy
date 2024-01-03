package xyz.funnyboy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 15:41:43
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册与发现功能
@EnableDiscoveryClient
public class ApiGatewayApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
