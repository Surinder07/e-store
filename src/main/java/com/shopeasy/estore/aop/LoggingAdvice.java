package com.shopeasy.estore.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoggingAdvice {
    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void myPointCutRestController(){
    }
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void myPointCutService(){
    }


    @Around("myPointCutRestController() || myPointCutService()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName=pjp.getSignature().getName();
        String className=pjp.getTarget().getClass().toString();
        Object [] array =pjp.getArgs();
        log.info("Method invoked" + className + " : " +
                methodName + "()" + "Arguments : " +
                mapper.writeValueAsString(array) + "Started at :" + new Date());
        Object object = pjp.proceed();
        log.info(className + " : " +
                methodName + "()" + "Response :" +
                mapper.writeValueAsString(object) + "Ended at :" + new Date());
        return object;
    }
    @AfterThrowing(pointcut = "myPointCutRestController() || myPointCutService()  ", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature()
                        .getName(), e.getCause() != null? e.getCause() : "NULL" + "Started at :" + new Date());
    }
}
