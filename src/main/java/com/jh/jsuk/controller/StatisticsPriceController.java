package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.StatisticsPrice;
import com.jh.jsuk.service.StatisticsPriceService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StatisticsPriceService statisticsPriceService;

    @ApiOperation("用户端-获取价格区间")
    @RequestMapping(value = "/getPriceArea",method = {RequestMethod.POST,RequestMethod.GET})
    public Result getPriceArea() {
        Page<StatisticsPrice> statisticsPricePage = statisticsPriceService.selectPage(
                new Page<>(1, 3),
                new EntityWrapper<StatisticsPrice>()
                        .orderBy(StatisticsPrice.COUNT, false));
        return new Result().success(statisticsPricePage.getRecords());
    }

}

