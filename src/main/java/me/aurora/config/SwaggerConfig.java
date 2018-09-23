package me.aurora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui的配置
 * api页面 /swagger-ui.html 或者/swagger/index
 * 如controller在不同的包中，@ComponentScan(basePackages = {"me.aurora.app.rest","..."})
 * @author 郑杰
 * @date 2018/09/23 9:04:23
 */

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"me.aurora.app.rest"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Aurora测试接口")
                .termsOfServiceUrl("https://gitee.com/hgpt/Aurora")
                .description("springboot集成swagger,druid,shiro,redis~后台管理脚手架")
                .contact(new Contact("Aurora1.0","https://www.zhengjie.me/blog/666.html","zhengjie@tom.com"))
                .version("1.0")
                .build();
    }

}
