package me.aurora.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.aurora.config.AuroraProperties;
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
@Aspect
@Component
public class LogAspect{

    @Autowired
    private AuroraProperties auroraProperties;

	@Autowired
	private SysLogService sysLogService;

	private ProceedingJoinPoint point;

	private long time;

	@Pointcut("@annotation(me.aurora.annotation.Log)")
	public void pointcut() {
	}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws JsonProcessingException {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if(auroraProperties.getOpenAopLog()){
			sysLogService.save(point, time);
        }
        return result;
    }
}
