package com.jh.jsuk;

import com.jh.jsuk.mq.RobbingOrderProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobbingOrderTest {

    @Autowired
    RobbingOrderProducer producer;

    @Test
    public void robbingOrder(){

        CountDownLatch latch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
//                latch.countDown();
//                try {
//                    UserOrderDTO data = new UserOrderDTO();
//                    data.setOrderId(385);
//                    data.setUserId(new Random().nextInt(3000));
//                    latch.await();
//                    producer.send(data);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }).start();

        }

    }


}
