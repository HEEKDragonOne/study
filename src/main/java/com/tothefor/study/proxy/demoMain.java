package com.tothefor.study.proxy;

import java.lang.reflect.Proxy;

public class demoMain {
    public static void main(String[] args) {

        Class[] ifs = {DemoInterface.class};

        DemoInterface temp = (DemoInterface) Proxy.newProxyInstance(demoMain.class.getClassLoader(), ifs, new DemoProxy<>(new DemoInterfaceImpl()));
        int res = temp.add(1, 2);
        System.out.println(res);
        temp.show("show");

    }
}
