package me.aurora.util.exception;

import me.aurora.domain.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郑杰
 * @date 2018/09/18 11:28:04
 * 自定义异常处理
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class AuroraExceptionHandler {

    @ExceptionHandler(value = AuroraException.class)
    @ResponseStatus
    public Object auroraException(AuroraException e) {
        return ResponseEntity.error(e.getId(),e.getMessage());
    }
}