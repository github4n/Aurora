package me.aurora.service.impl;

import me.aurora.annotation.Log;
import me.aurora.domain.SysLog;
import me.aurora.domain.User;
import me.aurora.repository.SysLogRepo;
import me.aurora.repository.spec.LogSpec;
import me.aurora.service.SysLogService;
import me.aurora.service.dto.SysLogDTO;
import me.aurora.service.mapper.SysLogMapper;
import me.aurora.util.*;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * @author 郑杰
 * @date 2018/08/23 9:57:45
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepo sysLogRepo;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    @Transactional(readOnly = true)
    public Map getLogInfo(LogSpec logSpec, Pageable pageable) {
        Page<SysLog> sysLogPage = sysLogRepo.findAll(logSpec,pageable);
        Page<SysLogDTO> sysLogDTOS = sysLogPage.map(sysLogMapper::toDto);
        return PageUtil.buildPage(sysLogDTOS.getContent(),sysLogPage.getTotalElements());
    }

    @Override
    public void save(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String username = null;
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                if("password".equals(paramNames[i])){
                    continue;
                }
                if("username".equals(paramNames[i])){
                    username = args[i]+"";
                }
                params += "  " + paramNames[i] + ": " + args[i];
            }
            if (params.length() > 255){
                params = params.substring(0,254);
            }
            sysLog.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(AddressUtils.getIpAddr(request));
        sysLog.setLocation(AddressUtils.getCityInfo(sysLog.getIp()));
        // 用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        sysLog.setUsername(user == null?"Login failed："+username:user.getUsername());
        sysLog.setTime((int) time);
        sysLogRepo.save(sysLog);
    }
}
