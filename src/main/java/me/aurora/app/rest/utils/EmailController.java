package me.aurora.app.rest.utils;

import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.utils.EmailConfig;
import me.aurora.domain.vo.EmailVo;
import me.aurora.service.EmailService;
import me.aurora.util.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 发送邮件
 * @author 郑杰
 * @date 2018/09/28 6:55:53
 */
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/index")
    public ModelAndView emailIndex(){
        EmailConfig emailConfig = emailService.findById(1L);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("email",emailConfig);
        if(emailConfig == null){
            request.setAttribute("email",new EmailConfig());
        }
        return new ModelAndView("/utils/email/index");
    }

    @Log("配置邮件")
    @PostMapping(value = "/config")
    public ResponseEntity emailConfig(@RequestBody @Validated(EmailConfig.Update.class) EmailConfig emailConfig){
        emailConfig.setId(1L);
        emailService.updateConfig(emailConfig,emailService.findById(1L));
        return ResponseEntity.ok();
    }

    @Log("发送邮件")
    @PostMapping(value = "send")
    public ResponseEntity send(@Validated(EmailVo.New.class) @RequestBody EmailVo emailVo) throws Exception {
        emailService.send(emailVo,emailService.findById(1L));
        return ResponseEntity.ok();
    }
}
