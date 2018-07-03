package com.jh.jsuk.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 价格区间统计表 前端控制器
 * </p>
 *
 * @author xcr
 * @since 2018-06-22
 */
@Api(tags = {"价格区间相关API:"})
@RestController
@RequestMapping(value = "/statisticsPrice",method = {RequestMethod.POST,RequestMethod.GET})
public class StatisticsPriceController {

}

