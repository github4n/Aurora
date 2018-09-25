package me.aurora.aspect;

import lombok.extern.slf4j.Slf4j;
import me.aurora.config.AuroraProperties;
import me.aurora.config.exception.AuroraException;
import me.aurora.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过切面保存操作日志
 * @author 郑杰
 * @date 2018/08/22 16:41:33
 */
@Slf4j
@Aspect
@Component
public class LogAspect{

    @Autowired
    private AuroraProperties auroraProperties;

	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(me.aurora.annotation.Log)")
	public void pointcut() {
	}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            if(e instanceof AuroraException){
                throw new AuroraException(((AuroraException) e).getId(),e.getMessage());
            }
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if(auroraProperties.getOpenAopLog()){
			sysLogService.save(point, time);
        }
        return result;
    }
}
