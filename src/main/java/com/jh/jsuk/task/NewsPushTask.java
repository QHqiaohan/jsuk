package com.jh.jsuk.task;

import com.jh.jsuk.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时处理线程池溢出的消息
 */
@Component
public class NewsPushTask {

    @Autowired
    NewsService newsService;

    @Scheduled(fixedRate =  10 * 60 * 1000)
    public void task(){
        newsService.fixedCall();
    }
}
