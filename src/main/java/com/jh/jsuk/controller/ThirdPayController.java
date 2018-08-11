package com.jh.jsuk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.ThirdPayService;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.WebhooksVerifyService;
import com.pingplusplus.exception.ChannelException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.Enumeration;

/**
 * Author:xyl
 * Date:2018/8/11 10:50
 * Description:ping++集成第三方支付
 */
@Api(tags = {"第三方支付相关操作API:"})
@Slf4j
@RestController
@RequestMapping("/thirdPay")
public class ThirdPayController {
    @Autowired
    private ThirdPayService thirdPayService;

    @ApiOperation(value = "第三方支付接口")
    @PostMapping
    public Result thirdPay(@RequestBody ThirdPayVo payVo) throws UnsupportedEncodingException, ChannelException, MessageException {

        return new Result().success(thirdPayService.thirdPay(payVo));
    }

    /**
     * 支付成功回调
     */
    @RequestMapping(value = "/webhooks")
    public void webhooks(HttpServletRequest request, HttpServletResponse response) throws IOException, MessageException {
        /*System.out.println("ping++　webhooks");*/
        request.setCharacterEncoding("UTF8");
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        String signature = null;
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if ("x-pingplusplus-signature".equals(key)) {
                signature = value;
            }
        }
        /*System.out.println("signature"+signature);*/
        // 获得 http body 内容
        StringBuffer eventJson = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            do {
                eventJson.append(reader.readLine());
            } while (reader.read() != -1);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        reader.close();
        JSONObject event = JSON.parseObject(eventJson.toString());
        boolean verifyRS = false;
        try {
            PublicKey publicKey = WebhooksVerifyService.getPubKey();
            /*  System.out.println(publicKey);*/
            verifyRS = WebhooksVerifyService.verifyData(eventJson.toString(), signature, publicKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (verifyRS) {
            /*System.out.println("签名验证成功");*/
            log.info("ping++回调 签名验证成功");
            if ("charge.succeeded".equals(event.get("type"))) {
                log.info("charge -- 支付成功---");
                JSONObject data = JSON.parseObject(event.get("data").toString());
                JSONObject object = JSON.parseObject(data.get("object").toString());
                String body = (String) object.get("body");
                ThirdPayVoChild payVoChild = (ThirdPayVoChild) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(body), ThirdPayVoChild.class);
                thirdPayService.chargeBack(payVoChild);
            } else {
                log.error("支付失败...");
            }
        } else {
            log.error("签名验证失败...");
            response.setStatus(500);
        }
    }
}
