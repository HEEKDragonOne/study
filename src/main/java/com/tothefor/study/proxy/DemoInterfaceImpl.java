package com.tothefor.study.proxy;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DemoInterfaceImpl implements DemoInterface {

    @Override
    public int add(int a, int b) {
        System.out.println("开始计算");
        int i = 1/0;
        return a + b;
    }

    @Override
    public void show(String id) {
        System.out.println("输出结果为：" + id);
    }
}
