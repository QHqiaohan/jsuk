package com.jh.jsuk;

import com.jh.jsuk.entity.User;
import com.jh.jsuk.mq.RobbingOrderProducer;
import com.jh.jsuk.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest {

    @Autowired
    private ShopService shopService;
    @Test
    public void test1(){
        User user = new User();
        user.setAddress("天府");
        shopService.findShopsByUserArea(user).forEach(System.err::println);
    }


}
