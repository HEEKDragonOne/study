package com.tothefor.study.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DependencyBean {
    public DependencyBean() {
        log.info("DependencyBean实例化完成");
    }
}
