package xyz.funnyboy.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Vod启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-28 21:47:28
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy",
                       exclude = DataSourceAutoConfiguration.class)
public class VodApplication
{
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
