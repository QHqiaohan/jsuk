package com.jh.jsuk.mq;

import com.jh.jsuk.entity.dto.RobbingExpressDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jh.jsuk.conf.QueueConfig.QUEUE_ROBBING_ORDER;

@Component
public class RobbingOrderProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(RobbingExpressDTO data) {
        rabbitTemplate.convertAndSend(QUEUE_ROBBING_ORDER, data);
    }

}
