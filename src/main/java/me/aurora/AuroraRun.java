package me.aurora;

import me.aurora.config.AuroraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({AuroraProperties.class})
/**
 * @author 郑杰
 * @date 2018/08/21 11:54:10
 */
public class AuroraRun extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuroraRun.class, args);
    }

    /**
     * 部署到tomcat需要添加如下代码
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuroraRun.class);
    }

    /**
     * 跳转到首页
     * @return
     */
    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }
}
