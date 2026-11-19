package com.tothefor.study.proxy;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class DemoAOP {

    @Pointcut("execution(* com.tothefor.study.proxy.DemoInterface.*(..))")
    public void points() {

    }


    @Before("points()")
    public void before() {
        System.out.println("before");
    }

    @After("points()")
    public void after() {
        System.out.println("after");
    }

    @AfterThrowing("points()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @AfterReturning("points()")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @Around("points()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around before");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("around after");
        return proceed;
    }
}
