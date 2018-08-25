package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.vo.ShopTodayMoneyVo;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopTodayMoneyService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 商家端-今日交易额明细 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"店铺交易额明细"})
@RestController
@RequestMapping("/shopTodayMoney")
public class ShopTodayMoneyController {

    @Autowired
    private ShopTodayMoneyService shopTodayMoneyService;
    @Autowired
    private ManagerUserService managerUserService;

    @ApiOperation("商家端-查看今日交易额列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "userId", value = "商家id", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "today", value = "年-月-日(格式:2018-07-24)", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getTodayMoney", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getTodayMoney(Integer current, Integer size, Integer userId, String today) {
        current = current == null ? 1 : current;
        size = size == null ? 10 : size;
        Page page = new Page(current, size);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (today == null || today.equals("") || !today.contains("-")) {
            today = sdf.format(new Date());
        }

        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if (managerUser == null) {
            return new Result().erro("该商家不存在");
        }
        Integer shopId = managerUser.getShopId();
        MyEntityWrapper<ShopTodayMoneyVo> ew = new MyEntityWrapper<>();
        Page moneyList = shopTodayMoneyService.getTodayMoneyList(page, ew, shopId, today);
        return new Result().success(moneyList);
    }

}

