package com.tothefor.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DemoProxy<T> implements InvocationHandler {

    private T obj;

    public DemoProxy(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象 方法前执行:" + method.getName());
        Object res = method.invoke(obj, args);
        System.out.println("代理对象 方法后执行:" + method.getName());
        return res;
    }
}
