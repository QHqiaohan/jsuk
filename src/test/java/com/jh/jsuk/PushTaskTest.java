package com.jh.jsuk;

import com.jh.jsuk.entity.News;
import com.jh.jsuk.service.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushTaskTest {

    @Autowired
    NewsService newsService;

    @Test
    public void testPush() throws Exception {


        News news = new News();
        news.setTitle("这是title");
        news.setContent("这是content收拾收拾试试是");
        newsService.push(news,235);


        Thread.sleep(5000);


    }

}