package com.jh.jsuk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsukApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        Integer num1 = 5;
        Integer num2 = 5;
        Integer num3 = 2;
        int fullStar = (num1 + num2 + num3) / 3;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fullStar);
    }

}
