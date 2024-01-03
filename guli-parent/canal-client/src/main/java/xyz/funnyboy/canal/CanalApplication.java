package xyz.funnyboy.canal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.funnyboy.canal.client.CanalClient;

import javax.annotation.Resource;

/**
 * Canal启动类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-02 23:32:32
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner
{
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        // 项目启动，执行canal客户端监听
        canalClient.run();
    }
}
