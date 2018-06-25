package com.jh.jsuk.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopRushBuyService;
import com.jh.jsuk.utils.DatecConvertUtils;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/shopRushBuy")
public class ShopRushBuyController {

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopRushBuyService shopRushBuyService;

    @ApiOperation("秒杀时间列表")
    @GetMapping("/getKillTime")
    public Result getKillTime() {
        List<ShopRushBuy> shopRushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                .between(ShopRushBuy.START_TIME, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date()))
                .orderBy(ShopRushBuy.START_TIME));
        if (CollectionUtils.isEmpty(shopRushBuyList)) {
            return new Result().erro("秒杀配置还未设置");
        } else {
            return new Result().success(shopRushBuyList);
        }
    }

    @ApiOperation(value = "根据时间查询秒杀商品", notes = "status:0=未开始,1=进行中,2=已结束")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @GetMapping("/findKillShopGoods")
    public Result findKillShopGoods(Page page, String killTime) {
        if (StrUtil.isBlank(killTime)) {
            return new Result().erro("参数错误");
        }
        LocalTime killT = DatecConvertUtils.StrToDate(killTime);
        // 获取当前时间
        LocalTime localTime = LocalTime.now();
        if (localTime.isAfter(killT)) {
            // 当前时间在秒杀之后,已结束
            List<ShopRushBuy> rushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                    .between(ShopRushBuy.START_TIME, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date()))
                    .orderBy(ShopRushBuy.START_TIME));
            if (CollectionUtils.isEmpty(rushBuyList)) {
                return new Result().erro("秒杀配置信息为空");
            }
            // 封装结果map
            Map<String, Object> result = Maps.newHashMap();
            packShopRushBuyData(page, rushBuyList, result, killTime);
            for (ShopRushBuy rushBuy : rushBuyList) {
                rushBuy.setStatus(2);
            }
            return new Result().success(result);
        } else if (localTime.isAfter(killT) == false) {
            // 当前时间没到秒杀时间,未开始
            List<ShopRushBuy> rushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                    .orderBy(ShopRushBuy.START_TIME));
            if (CollectionUtils.isEmpty(rushBuyList))
                return new Result().success("秒杀配置信息为空");
            // 封装结果map
            Map<String, Object> result = Maps.newHashMap();
            packShopRushBuyData(page, rushBuyList, result, killTime);
            for (ShopRushBuy rushBuy : rushBuyList) {
                rushBuy.setStatus(0);
            }
            return new Result().success(result);
        } else {
            // 秒杀进行中
            List<ShopRushBuy> rushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                    .orderBy(ShopRushBuy.START_TIME));
            if (CollectionUtils.isEmpty(rushBuyList))
                return new Result().success("秒杀配置信息为空");
            // 封装结果map
            Map<String, Object> result = Maps.newHashMap();
            packShopRushBuyData(page, rushBuyList, result, killTime);
            for (ShopRushBuy rushBuy : rushBuyList) {
                rushBuy.setStatus(1);
            }
            return new Result().success(result);
        }
    }

    public Page getData(Page page, Integer id) {
        Page pageInfo = shopGoodsService.selectPage(page, new EntityWrapper<ShopGoods>()
                .eq(ShopGoods.STATUS, 1)
                .orderBy(ShopGoods.SALE_AMONT));

        return pageInfo;
    }

    private void packShopRushBuyData(Page page, List<ShopRushBuy> list, Map<String, Object> result, String killTime) {
        // 遍历秒杀list
        for (ShopRushBuy shopRushBuy : list) {
            // 封装结果map
            if (DateUtil.isIn(DateUtil.parseDateTime(killTime), shopRushBuy.getStartTime(), shopRushBuy.getEndTime())) {
                Page data = getData(page, shopRushBuy.getId());
                result.putAll(BeanUtil.beanToMap(data));
            }
        }
    }

}

