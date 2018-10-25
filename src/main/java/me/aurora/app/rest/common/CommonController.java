package me.aurora.app.rest.common;

import me.aurora.annotation.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公共的controller
 * @author 郑杰
 * @date 2018/09/25 19:08:41
 */
@RestController
public class CommonController {

    @Log("访问swagger")
    @GetMapping(value = "/swagger/index")
    public ModelAndView index(){
        return new ModelAndView("/system/api/index");
    }
}
