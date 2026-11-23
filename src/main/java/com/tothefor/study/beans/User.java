package com.tothefor.study.beans;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User implements InitializingBean, DisposableBean {


    public User() {
        System.out.println("User无参构造执行...");
    }

    // 初始化方法，对应initMethod
    public void init() {
        System.out.println("配置Bean initMethod方法执行");
    }

    public void initImpl() {
        System.out.println("BeanFactoryPostProcessor的 initMethod方法执行");
    }

    // 销毁方法，对应destroyMethod
    public void destroyMethod() {
        System.out.println("配置Bean destroyMethod方法执行");
    }

    public void destroyMethodImpl() {
        System.out.println("BeanFactoryPostProcessor的 destroyMethod方法执行");
    }

    @PostConstruct
    public void initPostConstruct() {
        System.out.println("注解方式 initMethod方法执行");
    }

    // 容器销毁前执行销毁操作
    @PreDestroy
    public void destroyPreDestroy() {
        System.out.println("注解方式 destroyMethod方法执行");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("接口方式 initMethod方法执行");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("接口方式 destroyMethod方法执行");

    }
}
