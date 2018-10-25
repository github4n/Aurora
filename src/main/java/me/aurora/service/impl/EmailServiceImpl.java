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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 郑杰
 * @date 2018/09/28 7:11:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepo emailRepo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig updateConfig(EmailConfig emailConfig,EmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                emailConfig.setPass(EncryptHelper.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailRepo.saveAndFlush(emailConfig);
        return emailConfig;
    }

    @Override
    public EmailConfig findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<EmailConfig> emailConfig = emailRepo.findById(id);
        if(emailConfig.isPresent()){
            return emailConfig.get();
        } else {
            return null;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception {

        if(emailConfig == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"请先添加相应配置，再操作");
        }
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

        String content = emailVo.getContent() + "<p style=\"text-align: right;\">\n" +
                "    <span style=\"color: rgb(0, 112, 192);\"><br/></span>\n" +
                "</p>\n" +
                "<h4 style=\"text-align: right;\">\n" +
                "    <span style=\"color: rgb(0, 112, 192);\">------邮件来自</span><a href=\"http://xiswl.xyz\" target=\"_blank\" style=\"text-decoration: underline;\"><span style=\"color: rgb(0, 112, 192);\">Aurora</span></a><span style=\"color: rgb(0, 112, 192); text-decoration: none;\"></span><span style=\"color: rgb(0, 112, 192);\">的测试</span>\n" +
                "</h4>";
        /**
         * 发送
         */
        try {
            MailUtil.send(account,
                          emailVo.getTos(),
                          emailVo.getSubject(),
                          content,
                          emailVo.getIsHtml());
        }catch (Exception e){
            throw new AuroraException(HttpStatus.HTTP_INTERNAL_ERROR,e.getMessage());
        }
    }
}
