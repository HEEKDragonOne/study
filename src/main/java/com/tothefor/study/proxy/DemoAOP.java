package com.tothefor.study.proxy;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Order(1)
public class DemoAOP {

    @Pointcut("execution(* com.tothefor.study.proxy.DemoInterface.*(..))")
    public void points() {

    }


    @Before("points()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before：" + Arrays.toString(joinPoint.getArgs()));
    }

    @After("points()")
    public void after(JoinPoint joinPoint) {
        System.out.println("after" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(value = "points()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("afterThrowing：" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "points()")
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("afterReturning：" + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("points()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("around after");
        return proceed;
    }
}
