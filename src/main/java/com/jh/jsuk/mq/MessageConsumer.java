package com.jh.jsuk.mq;


import com.jh.jsuk.entity.Mq;
import com.jh.jsuk.entity.dto.MessageDTO;
import com.jh.jsuk.envm.mq.MqStatus;
import com.jh.jsuk.service.MqService;
import com.jh.jsuk.utils.JPushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.jh.jsuk.conf.QueueConfig.QUEUE_PUSH_MESSAGE;

@Slf4j
@Component
@RabbitListener(queues = QUEUE_PUSH_MESSAGE)
public class MessageConsumer {

    @Autowired
    MqService mqService;

    @RabbitHandler
    public void process(MessageDTO data) throws Exception {
        System.out.println("m22q");
        System.out.println("m22q");
        System.out.println("m22q");
        System.out.println("m22q");
        Mq mq = mqService.selectById(data.getId());
        System.out.println(mq);
        System.out.println("mq");
        System.out.println("mq");
        System.out.println("mq");
        System.out.println("mq");
        System.out.println("mq");
        System.out.println("mq");
        System.out.println("mq");
        if (mq == null || mq.isConsumed())
            return;
        try {
            JPushUtils.push(data.getUserType(), data.getUserId(), data.getContent(), data.getTitle());
            mq.setConsumeTime(new Date());
            mq.setStatus(MqStatus.CONSUME);
            mq.updateById();
        } catch (Exception e) {
            log.error(e.getMessage());
            mq.setFailureReason(e.getMessage());
            mq.updateById();
            throw e;
        }

    }

}
