package com.jh.jsuk.mq;

import com.jushang.entity.dto.UserOrderDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(UserOrderDTO data) {
        rabbitTemplate.convertAndSend("testQueue", data);
    }

}
