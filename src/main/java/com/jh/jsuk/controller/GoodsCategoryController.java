package com.jh.jsuk.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品类型相关API:")
@RestController
@RequestMapping(value = "/goodsCategory", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsCategoryController {


}

