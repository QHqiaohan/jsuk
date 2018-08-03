package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopGoodsSizeService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    ShopGoodsService shopGoodsService;

    @GetMapping("/list")
    public R list(Integer goodsId) {
        Wrapper<ShopGoodsSize> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopGoodsSize.SHOP_GOODS_ID, goodsId);
        return R.succ(shopGoodsSizeService.selectList(wrapper));
    }

    @GetMapping("/sizes")
    public R goodsSizes(Integer goodsId){
        return R.succ(shopGoodsSizeService.sizes(goodsId));
    }

    @ApiOperation("用户端-根据sizeId获取信息")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "goodsSizeId", value = "goodsSizeId", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/get")
    public R get(Integer goodsSizeId) {
        ShopGoodsSize size = shopGoodsSizeService.selectById(goodsSizeId);
        Map<String, Object> map = new HashMap<>();
        R r = R.succ(map);
        if (size == null) {
            return r;
        }
        map.put("sizeName", size.getSizeName());
        Integer shopGoodsId = size.getShopGoodsId();
        ShopGoods shopGoods = shopGoodsService.selectById(shopGoodsId);
        if (shopGoods == null)
            return r;
        map.put("goodsName", shopGoods.getGoodsName());
        map.put("mainImage", shopGoods.getMainImage());
        return r;
    }


}

