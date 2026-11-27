//package com.tothefor.study.proxy;
//
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//@Order(2)
//public class DemoAOP2 {
//
//    @Pointcut("execution(* com.tothefor.study.proxy.DemoInterface.*(..))")
//    public void points() {
//
//    }
//
//
//    @Before("points()")
//    public void before() {
//        System.out.println("2 before");
//    }
//
//    @After("points()")
//    public void after() {
//        System.out.println("2 after");
//    }
//
//    @AfterThrowing("points()")
//    public void afterThrowing() {
//        System.out.println("2 afterThrowing");
//    }
//
//    @AfterReturning("points()")
//    public void afterReturning() {
//        System.out.println("2 afterReturning");
//    }
//
//    @Around("points()")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println("2 around before");
//        Object proceed = proceedingJoinPoint.proceed();
//        System.out.println("2 around after");
//        return proceed;
//    }
//}
