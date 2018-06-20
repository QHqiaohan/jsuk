package com.jh.jsuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableScheduling
public class JsukApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(JsukApplication.class, args);
        InetAddress addr = InetAddress.getLocalHost();
        //获取本机ip
        String ip = addr.getHostAddress();
        //获取本机计算机名称
        String hostName = addr.getHostName();
        System.out.println("服务器IP   " + ip);
        System.out.println("服务器名称 " + hostName);
        System.err.println("swagger:http://" + ip + ":8080/swagger-ui.html");
    }
}
