package org.javaboy.vhr.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.javaboy.vhr.mapper.OpLogMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.OpLog;
import org.javaboy.vhr.utils.BaseContext;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * AOP 记录操作日志并存入数据库
 */
@Component
@Aspect
public class ExceptionLogAspect {

    @Autowired
    private OpLogMapper opLogMapper;


    //环绕通知
    @Around("execution(* org.javaboy.vhr.controller..*.*(..))")
    public Object opLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行目标方法
        Object result = joinPoint.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录操作日志
        OpLog opLog = new OpLog();
        // 这里只能获取到请求路径 可以配合Swagger的@ApiOperation注解获取到方法描述
        opLog.setOperate(request.getRequestURI());
        Hr currentHr = HrUtils.getCurrentHr();
        if (currentHr == null) {
            return result;
        }
        BaseContext.put("dateFormat", "yyyy-MM-dd HH:mm:ss");
        opLog.setHrid(currentHr.getId());
        opLog.setAdddate(new Date());
        opLogMapper.insert(opLog);
        return result;
    }


}
