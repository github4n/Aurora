package me.aurora.config.exception.handler;

import lombok.extern.slf4j.Slf4j;
import me.aurora.config.AuroraProperties;
import me.aurora.domain.ResponseEntity;
import me.aurora.config.exception.AuroraException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 郑杰
 * @date 2018/08/22 15:42:27
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private AuroraProperties auroraProperties;

    /**
     * 处理shiro异常
     * @return
     */
	@ExceptionHandler(value = AuthorizationException.class)
	public ModelAndView handleAuthorizationException() {
	    log.error("没有权限访问");
		return new ModelAndView(auroraProperties.getShiro().getUnauthorizedUrl());
	}

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.error(e.getMessage());
    }

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
	@ExceptionHandler(value = AuroraException.class)
	public ResponseEntity auroraException(AuroraException e) {
        log.error(e.getMessage());
		return ResponseEntity.error(e.getId(),e.getMessage());
	}

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @returns
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        String str[] = e.getBindingResult().getAllErrors().get(0).getCodes()[1].split("\\.");
        StringBuffer msg = new StringBuffer(str[1]+":");
        msg.append(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.error(msg.toString());
    }
}
