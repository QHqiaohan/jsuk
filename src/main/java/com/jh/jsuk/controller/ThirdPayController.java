package com.jh.jsuk.controller;

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
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    public void webhooks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        String signature = null;
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if ("x-pingplusplus-signature".equals(key)) {
                signature = request.getHeader(key);
                break;
            }
        }
        // 获得 http body 内容
        StringBuffer eventJson = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        while ((str = reader.readLine()) != null) {//一行一行的读取body体里面的内容；
            eventJson=eventJson.append(str);
        }
        reader.close();
        log.info(eventJson.toString());
        JSONObject event = JSONObject.fromObject(eventJson.toString());//转化成json对象
        PublicKey publicKey = WebhooksVerifyService.getPubKey();
        boolean verifyRS = WebhooksVerifyService.verifyData(eventJson.toString(), signature, publicKey);
        if (verifyRS) {
            if ("charge.succeeded".equals(event.get("type").toString())) {
                log.info("charge -- 支付成功---");
                JSONObject data = JSONObject.fromObject(event.get("data"));
                JSONObject object = JSONObject.fromObject(data.get("object"));
                JSONObject body = JSONObject.fromObject(object.get("body"));
                ThirdPayVoChild payVoChild = (ThirdPayVoChild) JSONObject.toBean(body);
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
