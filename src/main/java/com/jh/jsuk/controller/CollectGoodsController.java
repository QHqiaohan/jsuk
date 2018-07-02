package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.CollectGoods;
import com.jh.jsuk.service.CollectGoodsService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户商品收藏 前端控制器
 * </p>
 *
 * @author tj123
 * @since 2018-06-25
 */
@Api(tags = {"用户商品收藏:"})
@RestController
@RequestMapping("/collectGoods")
public class CollectGoodsController {

    @Autowired
    CollectGoodsService collectGoodsService;

    @ApiOperation("用户-判断商品是否在收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "int"),
    })
    @RequestMapping(value = "/isCollected", method = {RequestMethod.POST, RequestMethod.GET})
    public Result isCollect(@RequestParam Integer goodsId, Integer userId) {
        int count = collectGoodsService.selectCount(new EntityWrapper<CollectGoods>()
                .eq(CollectGoods.GOODS_ID, goodsId)
                .eq(CollectGoods.USER_ID, userId));
        if (count > 0) {
            return new Result().success("已收藏", 1);
        } else {
            return new Result().success("未收藏", 0);
        }
    }

    @ApiOperation("用户-添加/取消 收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(CollectGoods goods) {
        int count = collectGoodsService.selectCount(new EntityWrapper<CollectGoods>()
                .eq(CollectGoods.GOODS_ID, goods.getGoodsId())
                .eq(CollectGoods.USER_ID, goods.getUserId()));
        if (count > 0) {
            collectGoodsService.delete(new EntityWrapper<CollectGoods>()
                    .eq(CollectGoods.GOODS_ID, goods.getGoodsId())
                    .eq(CollectGoods.USER_ID, goods.getUserId()));
            return new Result().success("已取消收藏", 0);
        } else {
            goods.insert();
            return new Result().success("收藏成功", 1);
        }
    }


}

