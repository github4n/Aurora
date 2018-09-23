package me.aurora.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 郑杰
 * @date 2018/08/20 13:34:40
 */
@Configuration
public class ConfigurerAdapter implements WebMvcConfigurer {

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost/login.html就跳转到login页面了
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //exception
        registry.addViewController("/exception/404").setViewName("/exception/404");
        registry.addViewController("/exception/403").setViewName("/exception/403");
        registry.addViewController("/exception/500").setViewName("/exception/500");

        //首页
        registry.addViewController("/common/app").setViewName("/common/app");
        //设置
        registry.addViewController("/common/setting").setViewName("/common/setting");
        //跳转登陆注册页面
        registry.addViewController("/login.html").setViewName("/user/login");
        registry.addViewController("/signUp.html").setViewName("/register");
        registry.addViewController("/swagger/index").setViewName("/system/api/index");
    }
}
