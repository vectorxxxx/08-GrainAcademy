package xyz.funnyboy.servicebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 23/12/2023
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置组名
                .groupName("webApi")
                // 设置API信息
                .apiInfo(webApiInfo())
                // 设置扫描路径
                .select()
                // 排除admin路径下的接口
                // .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                // 构建
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                // API文档标题
                .title("网站-课程中心API文档")
                // API文档描述
                .description("本文档描述了课程中心微服务接口定义")
                // API版本号
                .version("1.0")
                // API联系人信息
                .contact(new Contact("VectorX", "http://www.funnyboy.xyz", "uxiahnan@outlook.com"))
                // 构建ApiInfo对象
                .build();
    }

}
