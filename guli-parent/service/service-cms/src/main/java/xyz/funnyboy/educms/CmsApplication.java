package xyz.funnyboy.educms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Cms启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 21:19:23
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
@EnableDiscoveryClient
public class CmsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
