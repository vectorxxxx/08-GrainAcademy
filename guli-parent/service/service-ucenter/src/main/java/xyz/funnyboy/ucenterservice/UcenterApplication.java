package xyz.funnyboy.ucenterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Ucenter启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 17:00:07
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
@EnableDiscoveryClient
public class UcenterApplication
{
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
