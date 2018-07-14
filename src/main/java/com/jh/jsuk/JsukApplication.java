package com.jh.jsuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//SpringBoot开启注解式事务支持
@EnableTransactionManagement
@ServletComponentScan
//定时任务支持
@EnableScheduling
@SpringBootApplication
public class JsukApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsukApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ项目启动成功ヾ(◍°∇°◍)ﾉﾞ");
    }
}