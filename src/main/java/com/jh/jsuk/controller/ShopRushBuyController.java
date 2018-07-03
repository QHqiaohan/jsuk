package com.jh.jsuk.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀信息配置 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "限时秒杀相关:")
@RestController
@RequestMapping(value = "/shopRushBuy", method = {RequestMethod.POST, RequestMethod.GET})
public class ShopRushBuyController {

}

