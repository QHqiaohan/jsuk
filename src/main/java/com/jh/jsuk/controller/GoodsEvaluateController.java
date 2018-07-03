package com.jh.jsuk.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品评价:")
@RestController
@RequestMapping(value = "/goodsEvaluate", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsEvaluateController {


}

