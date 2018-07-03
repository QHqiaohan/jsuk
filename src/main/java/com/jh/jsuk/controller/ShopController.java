package com.jh.jsuk.controller;


import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 店铺 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-05-24
 */
@Api(tags = "商家店铺相关API:")
@RestController
@RequestMapping("/shop")
public class ShopController {

    protected static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private UserRemainderService userRemainderService;
    @Autowired
    private ShopService shopService;

    @ApiOperation("到店支付、扫码支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "shopId", value = "商家id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "amount", value = "交易金额",
                    required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/pay", method = {RequestMethod.POST, RequestMethod.GET})
    public Result pay(Integer shopId, Integer userId, String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        Result<Object> result = new Result<>();
        if (!userRemainderService.hasRemain(userId, bigDecimal)) {
            return result.erro("当前用户余额不足！");
        }
        if (!shopService.isExist(shopId)) {
            return result.erro("商户不存在");
        }
        try {
            shopService.doDeal(shopId, userId, bigDecimal);
        } catch (Exception e) {
            logger.error("交易失败", e);
            return result.erro("交易失败");
        }
        return result.success();
    }

}

