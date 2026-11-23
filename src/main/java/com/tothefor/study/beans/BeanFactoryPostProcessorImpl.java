package com.tothefor.study.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


@Component
public class BeanFactoryPostProcessorImpl implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryPostProcessor】执行开始，容器中已注册的BeanDefinition数量：" + beanFactory.getBeanDefinitionCount());
        // 获取指定Bean的定义信息
        String beanName = "user";
        if (beanFactory.containsBeanDefinition(beanName)) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            // 指定Bean的初始化方法
            beanDefinition.setInitMethodName("initImpl");
            // 指定Bean的销毁方法
            beanDefinition.setDestroyMethodName("destroyMethodImpl");
        }
        System.out.println("【BeanFactoryPostProcessor】执行结束");
    }
}
