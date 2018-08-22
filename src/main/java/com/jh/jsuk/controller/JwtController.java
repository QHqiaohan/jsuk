package com.jh.jsuk.controller;

import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"令牌相关 不用管"})
@RestController()
public class JwtController {
    //过期
    @RequestMapping(value = "/overdue", method = {RequestMethod.POST, RequestMethod.GET})
    public Result overdue() {
        return new Result().overdue();
    }

    //没有登陆
    @RequestMapping(value = "/noLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public Result noLogin() {
        return new Result().noLogin();
    }

//    //没有找到该用户
//    @RequestMapping(value = "/nofound", method = {RequestMethod.POST, RequestMethod.GET})
//    public Result nofound() {
//        return new Result().nofound();
//    }
}
