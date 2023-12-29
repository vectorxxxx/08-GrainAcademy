package xyz.funnyboy.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Cms启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 21:19:23
 */
@SpringBootApplication
@ComponentScan({"xyz.funnyboy"})
@MapperScan("xyz.funnyboy.educms.mapper")
public class CmsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
