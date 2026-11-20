package com.tothefor.study;

import com.tothefor.study.proxy.DemoInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StudyApplicationTests {

    @Resource
    private DemoInterface demoInterface;

    @Test
    void contextLoads() {

        System.out.println(demoInterface.add(1, 2));

    }

}
