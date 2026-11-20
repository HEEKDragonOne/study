package com.tothefor.study.beans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // 手动注册Bean，并指定初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destroyMethod")
    public User user() {
        return new User();
    }

}
