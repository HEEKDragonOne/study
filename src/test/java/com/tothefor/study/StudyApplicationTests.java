package com.tothefor.study;

import com.tothefor.study.beans.AwareDemo;
import com.tothefor.study.beans.User;
import com.tothefor.study.proxy.DemoInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StudyApplicationTests {

    @Resource
    private DemoInterface demoInterface;

    @Resource
    private User user;

    @Resource
    private AwareDemo awareDemo;

    @Test
    void contextLoads() {

//        System.out.println(demoInterface.add(1, 2));
//        System.out.println(user);
        System.out.println(awareDemo);

    }

}
