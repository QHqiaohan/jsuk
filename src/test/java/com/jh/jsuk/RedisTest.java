package com.jh.jsuk;

import com.github.tj123.common.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisUtils redisUtils;
    @Test
    public void testRedis() throws Exception {



        redisUtils.setStr("hahahaha","搭搭撒撒爱谁谁啊啊");
        System.out.println(redisUtils.get("hahahaha"));


        UserTest value = new UserTest();
        value.setId(12);
        value.setName("sdfasdfasdfasdfasdfasdf");
        redisUtils.set("sdfasdf", value);
        System.out.println(redisUtils.get("sdfasdf",UserTest.class));
        redisUtils.set("tims",value,10);
    }

}