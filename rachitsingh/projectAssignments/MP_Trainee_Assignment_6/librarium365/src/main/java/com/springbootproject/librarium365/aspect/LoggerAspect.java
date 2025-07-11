package com.springbootproject.librarium365.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    // Pointcut to target all methods in service layer
    @Pointcut("execution(* com.springbootproject.librarium365.service..*(..))")
    public void serviceMethods() {}

    // Log method entry
    @Before("serviceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering method: {} with args: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    // Log method exit
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    // Log exception thrown during execution
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method: {} - {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage());
    }

    // Log execution time
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // proceed with method call
        long endTime = System.currentTimeMillis();

        logger.info("Method {} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                (endTime - startTime));

        return result;
    }
}
