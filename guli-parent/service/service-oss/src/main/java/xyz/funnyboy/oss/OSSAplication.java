package xyz.funnyboy.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * OSS启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:14:33
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"xyz.funnyboy"})
public class OSSAplication
{
    public static void main(String[] args) {
        SpringApplication.run(OSSAplication.class, args);
    }
}
