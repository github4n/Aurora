package me.aurora.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件时，接收参数的类
 * @author 郑杰
 * @date 2018/09/28 12:02:14
 */
@Data
public class EmailVo {

    /**
     * 收件人，支持多个收件人，用逗号分隔
     */
    @NotBlank(groups = New.class)
    private String tos;

    @NotBlank(groups = New.class)
    private String subject;

    @NotBlank(groups = New.class)
    private String content;
    /**
     * 是否为html，默认为true
     */
    private Boolean isHtml = true;

    public ArrayList<String> getTos() {
        String str[] = this.tos.split("[,，；;]");
        ArrayList<String> list = new ArrayList<>();
        for (String s : str) {
            list.add(s);
        }
        return list;
    }

    public interface New {}
}
