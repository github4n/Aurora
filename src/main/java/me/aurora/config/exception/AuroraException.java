package me.aurora.config.exception;

import lombok.Getter;

/**
 * @author 郑杰
 * @date 2018/09/18 11:27:40
 * 通用异常处理类
 */
@Getter
public class AuroraException extends RuntimeException{

    private int id;

    public AuroraException(int id, String msg){
        super(msg);
        this.id = id;
    }
}
