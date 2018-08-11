package com.jh.jsuk.controller;

import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.ThirdPayService;
import com.jh.jsuk.utils.Result;
import com.pingplusplus.exception.ChannelException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * Author:xyl
 * Date:2018/8/11 10:50
 * Description:ping++集成第三方支付
 */
@Api(tags = {"第三方支付相关操作API:"})
@RestController
@RequestMapping("/thirdPay")
public class ThirdPayController {
    @Autowired
    private ThirdPayService thirdPayService;

    @PostMapping
    public Result thirdPay(@RequestBody ThirdPayVo payVo) throws UnsupportedEncodingException, ChannelException, MessageException {

        return new Result().success(thirdPayService.thirdPay(payVo));
    }
}
