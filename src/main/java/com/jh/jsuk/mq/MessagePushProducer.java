package com.jh.jsuk.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.jsuk.entity.Mq;
import com.jh.jsuk.entity.dto.MessageDTO;
import com.jh.jsuk.envm.MqStatus;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.jh.jsuk.conf.QueueConfig.QUEUE_PUSH_MESSAGE;

@Slf4j
@Component
public class MessagePushProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void pushUser(MessageDTO data) {
        data.setUserType(UserType.USER);
        send(data);
    }

    public void pushShop(MessageDTO data) {
        data.setUserType(UserType.SHOP);
        send(data);
    }

    public void pushDistp(MessageDTO data) {
        data.setUserType(UserType.DISTRIBUTION);
        send(data);
    }

    private void send(MessageDTO data) {
        Mq mq = new Mq();
        String id = UuidUtil.getUUID();
        data.setId(id);
        mq.setId(id);
        mq.setQueueName(QUEUE_PUSH_MESSAGE);
        mq.setCreateTime(new Date());
        mq.setStatus(MqStatus.CREATE);
        try {
            mq.setBody(new ObjectMapper().writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        mq.insert();
        rabbitTemplate.convertAndSend(QUEUE_PUSH_MESSAGE, data);
        mq.setStatus(MqStatus.SENT);
        mq.setSentTime(new Date());
        mq.updateById();
    }

}
