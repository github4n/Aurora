package me.aurora.util;

import cn.hutool.http.HttpStatus;
import me.aurora.config.exception.AuroraException;

import java.util.Optional;

/**
 * 验证工具
 * @author 郑杰
 * @date 2018/09/21 10:35:03
 */
public class ValidationUtil {

    /**
     * 验证空
     * @param optional
     */
    public static void isNull(Optional optional, String msg){
        System.out.println(msg);
        if(!optional.isPresent()){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,msg);
        }
    }

    /**
     * 验证是否为邮箱
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return string.matches(regEx1);
    }
}
