package com.jh.jsuk.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueueConfig {
    @Bean
    public Queue helloQueue() {
        return new Queue("testQueue");
    }

}
