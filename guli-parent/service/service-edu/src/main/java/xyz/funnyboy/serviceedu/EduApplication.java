package xyz.funnyboy.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author VectorX
 * @version V1.0
 * @description 启动类
 * @date 23/12/2023
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
public class EduApplication
{
    public static void main(final String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
