package me.aurora.service.impl;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpStatus;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.utils.EmailConfig;
import me.aurora.domain.vo.EmailVo;
import me.aurora.repository.EmailRepo;
import me.aurora.service.EmailService;
import me.aurora.util.EncryptHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 郑杰
 * @date 2018/09/28 7:11:56
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepo emailRepo;

    @Override
    public EmailConfig updateConfig(EmailConfig emailConfig) {
        emailConfig.setId(1L);
        try {
            emailConfig.setPass(EncryptHelper.desEncrypt(emailConfig.getPass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailRepo.saveAndFlush(emailConfig);
        return emailConfig;
    }

    @Override
    public EmailConfig findById(Long id) {
        EmailConfig emailConfig = emailRepo.findById(id).get();
        return emailConfig;
    }

    @Override
    public void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception {

        /**
         * 封装
         */
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        account.setPass(EncryptHelper.desDecrypt(emailConfig.getPass()));
        /**
         * 判断是否为ssl形式发送
         */
        if(emailConfig.getSslEnable()){
            account.setFrom(emailConfig.getUser()+"<"+emailConfig.getFromUser()+">");
        } else {
            account.setFrom(emailConfig.getFromUser());
            account.setUser(emailConfig.getUser());
        }
        //是否ssl方式发送
        account.setStartttlsEnable(emailConfig.getSslEnable());
        /**
         * 发送
         */
        try {
            MailUtil.send(account,
                          emailVo.getTos(),
                          emailVo.getSubject(),
                          emailVo.getContent(),
                          emailVo.getIsHtml());
        }catch (Exception e){
            throw new AuroraException(HttpStatus.HTTP_INTERNAL_ERROR,e.getMessage());
        }
    }
}
