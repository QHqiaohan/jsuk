package com.jh.jsuk.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Web层日志切面
 */
@Slf4j
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();
//    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * com.jh.jsuk.controller.*..*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
//        System.out.println("\r\n");
        log.info("地址 : " + request.getRequestURL().toString());
        log.info("请求方式 : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("执行的方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        try {
            log.info("返回内容 : {}", new ObjectMapper().writeValueAsString(ret));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("花费时间 : " + (System.currentTimeMillis() - startTime.get()) + "毫秒");
    }


}
