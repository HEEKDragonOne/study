package com.tothefor.study.beans;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Slf4j
@Component
public class LifecycleDemoBean {

    private DependencyBean dependencyBean;

    /**
     * 构造方法：对应实例化阶段
     */
    public LifecycleDemoBean() {
        log.info("【阶段2-实例化】LifecycleDemoBean构造方法执行，此时dependencyBean是否为null：{}", dependencyBean == null);
    }

    /**
     * 依赖注入：对应属性填充阶段
     *
     * @param dependencyBean 依赖的Bean实例
     */
    @Autowired
    public void setDependencyBean(DependencyBean dependencyBean) {
        this.dependencyBean = dependencyBean;
        log.info("【阶段3-属性填充】setDependencyBean执行，此时dependencyBean是否为null：{}", dependencyBean == null);
    }

    @PostConstruct
    public void init() {
        log.info("初始化阶段执行，dependencyBean已完成注入");
    }
}
