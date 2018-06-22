package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopGoodsSizeService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品相关API:")
@RestController
@RequestMapping("/shopGoods")
public class ShopGoodsController {

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

    @ApiOperation("用户端-根据店铺内部的分类-属性查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsByAttributeId")
    public Result getShopGoodsByAttributeId(@ApiParam(value = "属性ID,不传为全部商品") Integer attributeId, Page page) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.shopGoodsListByAttributeId(page, ew, attributeId);
        if (CollectionUtils.isEmpty(goodsPage.getRecords())) {
            return new Result().success(null);
        } else {
            return new Result().success(goodsPage);
        }
    }

    @ApiOperation(value = "用户端-根据综合/价格/销量/新品查询商品", notes = "type:1=价格降序/-1升序,2=销量,3=新品,默认综合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsBy")
    public Result getShopGoodsBy(Page page, Integer type) {
        // 按价格查询
        if (type == 1 || type == -1) {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsOrderBySalesPrice(page, ew, type);
            return new Result().success(goodsPage);
        } else {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsBy(page, ew, type);
            return new Result().success(goodsPage);
        }
    }

    @ApiOperation(value = "用户端-根据商品类型获取商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsByCategoryId")
    public Result getShopGoodsByCategoryId(Page page, Integer type) {
        // 按价格查询
        if (type == 1 || type == -1) {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsOrderBySalesPrice(page, ew, type);
            return new Result().success(goodsPage);
        } else {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsBy(page, ew, type);
            return new Result().success(goodsPage);
        }
    }

}

