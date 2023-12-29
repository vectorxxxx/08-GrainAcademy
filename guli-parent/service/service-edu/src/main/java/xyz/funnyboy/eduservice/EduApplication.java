package xyz.funnyboy.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author VectorX
 * @version V1.0
 * @description 启动类
 * @date 23/12/2023
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 启动服务注册发现（Nacos）
@EnableDiscoveryClient
// 启动OpenFeign客户端
@EnableFeignClients
public class EduApplication
{
    public static void main(final String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
