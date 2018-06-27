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

    @Bean
    public Queue helloQueue() {
        return new Queue(QUEUE_ROBBING_ORDER);
    }

}
