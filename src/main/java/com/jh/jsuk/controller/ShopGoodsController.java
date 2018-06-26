package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.GoodsLabel;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.StatisticsPrice;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @Autowired
    private GoodsLabelService goodsLabelService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private StatisticsPriceService statisticsPriceService;

    @ApiOperation("用户端-根据店铺内部的分类-属性查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "attributeId", value = "属性ID,不传=全部商品", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsByAttributeId")
    public Result getShopGoodsByAttributeId(Integer attributeId, Page page, Integer shopId) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.shopGoodsListByAttributeId(page, ew, attributeId, shopId);
        return new Result().success(goodsPage);
    }

    @ApiOperation(value = "用户端-店铺内部的全部商品-根据综合/价格/销量/新品查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "1=价格降序/-1升序,2=销量,3=新品,不传=默认综合", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", required = true, value = "店铺ID", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsBy")
    public Result getShopGoodsBy(Page page, Integer type, Integer shopId) {
        // 按价格查询
        if (type != null) {
            if (type == 1 || type == -1) {
                MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
                Page goodsPage = shopGoodsService.getShopGoodsOrderBySalesPrice(page, ew, type, shopId);
                return new Result().success(goodsPage);
            }
        } else {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsBy(page, ew, type, shopId);
            return new Result().success(goodsPage);
        }
        return new Result().success("暂无数据", null);
    }

    @ApiOperation(value = "用户端-根据商品类型获取商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopGoodsByCategoryId")
    public Result getShopGoodsByCategoryId(Page page, Integer categoryId) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByCategoryId(page, ew, categoryId);
        return new Result().success(goodsPage);
    }

    @ApiOperation(value = "用户端-商品类型里的-根据综合/价格/销量查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer"),
    })
    @GetMapping("/getShopGoodsOnCategoryBy")
    public Result getShopGoodsOnCategoryBy(Page page, Integer categoryId, @ApiParam("1=价格降序/-1升序,2=销量") Integer type) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsOnCategoryBy(page, ew, type, categoryId);
        return new Result().success(goodsPage);
    }

    @ApiOperation(value = "用户端-根据筛选条件查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsType", value = "1=包邮,2=促销,3=新品", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "lowPrice", value = "最低价格", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "highPrice", value = "最高价格", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopGoodsByServiceOrPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsByServiceOrPrice(Page page, Integer goodsType, String lowPrice, String highPrice, Integer categoryId) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByServiceOrPrice(page, ew, goodsType, lowPrice, highPrice, categoryId);
        // 每价格搜索一次,添加价格区间统计
        if (lowPrice != null && highPrice != null) {
            StatisticsPrice price = statisticsPriceService.selectOne(new EntityWrapper<StatisticsPrice>()
                    .eq(StatisticsPrice.LOW_PRICE, lowPrice)
                    .eq(StatisticsPrice.HIGH_PRICE, highPrice));
            if (price != null) {
                StatisticsPrice statisticsPrice = new StatisticsPrice();
                statisticsPrice.setLowPrice(lowPrice);
                statisticsPrice.setLowPrice(highPrice);
                statisticsPrice.setId(price.getId());
                Integer count = price.getCount();
                count++;
                statisticsPrice.setCount(count);
                statisticsPrice.updateById();
            } else {
                StatisticsPrice statisticsPrice = new StatisticsPrice();
                statisticsPrice.setLowPrice(lowPrice);
                statisticsPrice.setLowPrice(highPrice);
                statisticsPrice.setPercentage("1%");
                statisticsPrice.insert();
            }
        }
        return new Result().success(goodsPage);
    }

    @ApiOperation("用户端-根据商品ID查看商品信息")
    @GetMapping("/getShopGoodsById")
    public Result getShopGoodsById(@ApiParam(value = "商品ID", required = true) @RequestParam Integer id) {
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        GoodsSizeVo goodsSizeVo = shopGoodsService.getShopGoodsById(id);
        if (goodsSizeVo == null) {
            map.put("shopGoods", "暂无数据");
        } else {
            map.put("shopGoods", goodsSizeVo);
            // 获取标签ID
            Integer labelId = goodsSizeVo.getGoodsLabelId();
            GoodsLabel goodsLabel = goodsLabelService.selectOne(new EntityWrapper<GoodsLabel>()
                    .eq(GoodsLabel.ID, labelId)
                    .eq(GoodsLabel.IS_DEL, 1)
                    .orderBy(GoodsLabel.RANK, false));
            map.put("goodsLabel", goodsLabel);
        }
        return new Result().success(map);
    }

    @ApiOperation("用户端-商品搜索&店铺搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "1=商品,2=店铺,3=两者", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "name", value = "关键字", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "attributeId", value = "属性ID", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "modularId", value = "模块ID", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getShopListByLike")
    public Result getShopListByLike(Page page, Integer type, String name, Integer modularId, Integer attributeId) {
        if (type == 2) {
            // 店铺模糊查询
            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                    .eq(Shop.CAN_USE, 1)
                    .eq(Shop.MODULAR_ID, modularId)
                    .like(Shop.SHOP_NAME, name)
                    .orderBy(Shop.TOTAL_VOLUME, false));
            return new Result().success(shopPage);
        } else if (type == 1) {
            // 商品模糊查询
            MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsByLikeName(page, ew, type, name, attributeId);
            return new Result().success(goodsPage);
        } else {
            // 店铺&商品
            Map<String, Object> map = MapUtil.newHashMap();

            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                    .eq(Shop.CAN_USE, 1)
                    .eq(Shop.MODULAR_ID, modularId)
                    .like(Shop.SHOP_NAME, name)
                    .orderBy(Shop.TOTAL_VOLUME, false));

            MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsByLikeName(page, ew, type, name, attributeId);
            map.put("shop", shopPage);
            map.put("shopGoods", goodsPage);

            return new Result().success(map);
        }
    }

}

