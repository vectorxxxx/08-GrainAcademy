package xyz.funnyboy.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Sms启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-30 15:34:48
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy",
                       exclude = DataSourceAutoConfiguration.class)
// 开启服务注册发现
@EnableDiscoveryClient
public class SmsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
