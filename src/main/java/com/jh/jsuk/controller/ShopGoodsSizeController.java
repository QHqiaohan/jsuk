package com.jh.jsuk.controller;


import com.jh.jsuk.service.ShopGoodsSizeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品规格 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"商品规格相关:(商家端-活动列表)"})
@RestController
@RequestMapping("/shopGoodsSize")
public class ShopGoodsSizeController {

    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

}

