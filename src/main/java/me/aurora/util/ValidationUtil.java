package me.aurora.util;

import cn.hutool.http.HttpStatus;
import me.aurora.config.exception.AuroraException;

/**
 * 验证工具
 * @author 郑杰
 * @date 2018/09/21 10:35:03
 */
public class ValidationUtil {

    /**
     * 验证空
     * @param object
     */
    public static void isNull(Object object,String msg){
        System.out.println(msg);
        if(object == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,msg);
        }
    }
}
