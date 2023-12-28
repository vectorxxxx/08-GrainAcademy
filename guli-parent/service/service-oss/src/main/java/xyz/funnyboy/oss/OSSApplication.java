package xyz.funnyboy.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * OSS启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:14:33
 */
@SpringBootApplication(scanBasePackages = {"xyz.funnyboy"},
                       exclude = DataSourceAutoConfiguration.class)
public class OSSApplication
{
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class, args);
    }
}
