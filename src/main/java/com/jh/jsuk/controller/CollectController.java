package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Collect;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.service.CollectService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2017-11-13
 */
@Api(tags = {"收藏:"})
@RestController
@RequestMapping(value = "/collect", method = {RequestMethod.POST, RequestMethod.GET})
public class CollectController {

    @Autowired
    private CollectService collectService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopGoodsService shopGoodsService;

    @ApiOperation("添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopId", required = true, value = "店铺id", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@ModelAttribute Collect collect) {
        int count = collectService.selectCount(new EntityWrapper<Collect>()
                .eq(Collect.SHOP_ID, collect.getShopId())
                .eq(Collect.USER_ID, collect.getUserId()));
        if (count > 0) {
            collectService.delete(new EntityWrapper<Collect>()
                    .eq(Collect.SHOP_ID, collect.getShopId())
                    .eq(Collect.USER_ID, collect.getUserId()));
            return new Result().success("已取消收藏", 0);
        } else {
            collect.insert();
            return new Result().success("收藏成功", 1);
        }
    }

    @ApiOperation("判断该店铺是否在收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", required = true, value = "店铺id", paramType = "query", dataType = "int"),
    })
    @RequestMapping("/isCollect")
    public Result isCollect(@RequestParam Integer shopId, Integer userId) {
        int count = collectService.selectCount(new EntityWrapper<Collect>()
                .eq(Collect.SHOP_ID, shopId)
                .eq(Collect.USER_ID, userId));
        if (count > 0) {
            return new Result().success("已收藏", 1);
        } else {
            return new Result().success("未收藏", 0);
        }
    }

    @ApiOperation(value = "用户商品收藏列表", notes = "展示收藏商品列表，进入页面提取20个数据，下拉加载更多，每次加载20个")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @PostMapping("/getList")
    public Result getList(@RequestParam @ApiParam(hidden = true) Integer userId, Page page) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        List<Collect> collectList = collectService.selectList(new EntityWrapper<Collect>()
                .eq(Collect.USER_ID, userId));
        // 封装收藏的商品数据
        List<ShopGoods> list = new ArrayList<>();
        for (Collect collect : collectList) {
            // 收藏的商品id
            Integer shopId = collect.getShopId();
            Page shopGoodsPage = shopGoodsService.selectPage(page, new EntityWrapper<ShopGoods>()
                    .eq(ShopGoods.ID, shopId));
            List<ShopGoods> records = shopGoodsPage.getRecords();
            for (ShopGoods shopGoods : records) {
                list.add(shopGoods);
            }
        }
        map.put("total", collectList.size());
        map.put("current", page.getCurrent());
        map.put("size", page.getSize());
        map.put("pages", (collectList.size() - 1 + page.getSize()) / page.getSize());
        map.put("records", list);
        return new Result().success(map);
    }

}