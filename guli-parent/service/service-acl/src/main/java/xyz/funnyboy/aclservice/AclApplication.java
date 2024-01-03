package xyz.funnyboy.aclservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 权限管理启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 21:58:58
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现功能
@EnableDiscoveryClient
public class AclApplication
{
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
