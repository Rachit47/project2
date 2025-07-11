package com.springlab.checkpoint6.LoanApprovalSystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.springlab.checkpoint6.LoanApprovalSystem.service.LoanService.*(..))")
    public void before() {
        System.out.println("[LOG] Method started");
    }

    @AfterReturning("execution(* com.springlab.checkpoint6.LoanApprovalSystem.service.LoanService.*(..))")
    public void afterReturning() {
        System.out.println("[LOG] Method completed");
    }

    @Around("execution(* com.springlab.checkpoint6.LoanApprovalSystem.service.LoanService.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("[PERF] " + joinPoint.getSignature() + " executed in " + (end - start) + " ms");
        return result;
    }
}
