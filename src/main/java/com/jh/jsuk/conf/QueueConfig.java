package com.jh.jsuk.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueueConfig {

    /**
     * 抢订单
     */
    public static final String QUEUE_ROBBING_ORDER = "robbing_order";

    /**
     * 推送消息
     */
    public static final String QUEUE_PUSH_MESSAGE = "push_message";

    @Bean(name = "robbingOrder")
    public Queue robbingOrderQueue() {
        return new Queue(QUEUE_ROBBING_ORDER);
    }

    @Bean(name = "pushMessage")
    public Queue pushMessageQueue() {
        return new Queue(QUEUE_PUSH_MESSAGE);
    }

}
