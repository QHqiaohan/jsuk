package com.jh.jsuk.controller;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.envm.ShopRushBuyStatus;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopRushBuyService;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//import com.jh.jsuk.service.ShopRushBuySizeService;

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

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopRushBuyService shopRushBuyService;
//    @Autowired
//    private ShopRushBuySizeService shopRushBuySizeService;

    @GetMapping("/page")
    public R listPage(Page page) {
        Wrapper<ShopRushBuy> wrapper = new EntityWrapper<>();
        wrapper.ne(ShopRushBuy.IS_DEL, 1);
        return R.succ(shopRushBuyService.selectPage(page, wrapper));
    }

    @GetMapping("/list")
    public R list() {
        Wrapper<ShopRushBuy> wrapper = new EntityWrapper<>();
        wrapper.ne(ShopRushBuy.IS_DEL, 1)
            .eq(ShopRushBuy.IS_USE, 1);
        return R.succ(shopRushBuyService.selectList(wrapper));
    }

    @PostMapping("/update")
    public R update(ShopRushBuy rushBuy) {
        rushBuy.updateById();
        return R.succ();
    }

    @PutMapping
    public R add(ShopRushBuy rushBuy) {
        rushBuy.insert();
        return R.succ();
    }

    @PatchMapping
    public R edit(ShopRushBuy rushBuy) {
        rushBuy.updateById();
        return R.succ();
    }

    @DeleteMapping
    public R delete(ShopRushBuy shopRushBuy) {
        shopRushBuy.setIsDel(1);
        shopRushBuy.updateById();
        return R.succ();
    }

    @GetMapping("/get")
    public R get(Integer id) {
        return R.succ(shopRushBuyService.selectById(id));
    }

    @ApiOperation("秒杀时间列表")
    @RequestMapping("/getKillTime")
    public Result getKillTime() {
        List<ShopRushBuy> list = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
            .eq(ShopRushBuy.IS_USE, 1)
            .ne(ShopRushBuy.IS_DEL, 1)
            .orderBy(ShopRushBuy.START_TIME));
        DateTime dateTime = new DateTime("1970-01-01", "yyyy-MM-dd");
        List<Map<String, Object>> times = new ArrayList<>();
        for (ShopRushBuy shopRushBuy : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", shopRushBuy.getId());
            Date startTime = shopRushBuy.getStartTime();
            map.put("startTime", startTime);
            Date endTime = shopRushBuy.getEndTime();
            map.put("endTime", endTime);
            if (dateTime.isBeforeOrEquals(startTime)) {
                map.put("status", ShopRushBuyStatus.NOT_STARTED);
            } else if (dateTime.isIn(startTime, endTime)) {
                map.put("status", ShopRushBuyStatus.ON_GOING);
            } else if (dateTime.isAfterOrEquals(endTime)) {
                map.put("status", ShopRushBuyStatus.OVER);
            } else {
                map.put("status", null);
            }
            times.add(map);
        }
        if (CollectionUtils.isEmpty(list)) {
            return new Result().success("秒杀配置还未设置", null);
        } else {
            return new Result().success(times);
        }
    }

    @ApiOperation(value = "根据时间查询秒杀商品")//, notes = "status:0=未开始,1=进行中,2=已结束")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "id", value = "秒杀时间ID", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping("/findKillShopGoods")
    public Result findKillShopGoods(Page page, Integer id) {
        Result r = new Result();
        if (id == null) {
            return r.erro("参数错误");
        }
        ShopRushBuy rushBuyTime = shopRushBuyService.selectById(id);
        if (rushBuyTime == null) {
            return r.erro("秒杀配置还未设置");
        }
        page = shopRushBuyService.getShopRushBuyList(page, id);
        return r.success(page);
    }


}

