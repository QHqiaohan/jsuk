package com.jh.jsuk;

import com.jh.jsuk.service.UserAuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {

    @Autowired
    UserAuthenticationService userAuthenticationService;



    @Test
    public void test(){

        System.out.println(userAuthenticationService.selectList(null));

    }

}
