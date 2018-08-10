package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.ModularPortalVo;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ModularPortalService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模块分类 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-06-20
 */
@Api(tags = {"模块相关API--(便捷生活/乡村旅游/二手市场不需要调这个):"})
@RestController
@RequestMapping("/modularPortal")
public class ModularPortalController {

    @Autowired
    private ModularPortalService modularPortalService;
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopService shopService;
    @Autowired
    ManagerUserService managerUserService;

    @ApiOperation(value = "用户端-根据模块ID获取店铺/商品列表")
    @RequestMapping(value = "/getShopAndGoodsByModular", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopAndGoodsByModular(@ApiParam(value = "模块ID", required = true) Integer modularId) {
        // 封装数据map
        Map<String, Object> map = MapUtil.newHashMap();

        /**
         * 店铺列表
         */
        Page<Shop> shopPage = shopService.selectPage(
            new Page<>(1, 3),
            new EntityWrapper<Shop>()
                // 是否可用  0不可用 1可用
                .eq(Shop.CAN_USE, 1)
                // 是否推荐,0=不推荐,1=推荐
                .eq(Shop.IS_RECOMMEND, 1)
                .eq(Shop.MODULAR_ID, modularId)
                .orderBy(Shop.TOTAL_VOLUME, false));

        List<Shop> records = shopPage.getRecords();
        List<ShopTelPhoneVo> list = new ArrayList<>();
        for (Shop record : records) {
            ShopTelPhoneVo vo = record.toTelPhoneVo();
            list.add(vo);
            EntityWrapper<ManagerUser> wrapper = new EntityWrapper<>();
            wrapper.eq(ManagerUser.SHOP_ID, record.getId());
            ManagerUser user = managerUserService.selectOne(wrapper);
            if (user != null) {
                vo.setTelPhone(user.getPhone());
            }
        }
        map.put("shop", list);
        /**
         * 商品列表
         */
        // 商品数据
        List<GoodsSalesPriceVo> goodsSalesPriceVos = shopGoodsService.findShopGoodsByModularId(modularId);
        map.put("shopGoods", goodsSalesPriceVos);
        return new Result().success(map);
    }

    @ApiOperation(value = "用户端-根据模块ID获取更多店铺", notes = "modularId=0 -->获取首页更多精选商家列表")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "kw", value = "搜索关键字",
            paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/shopListByModularId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result shopListByModularId(Page page, @ApiParam(value = "模块ID", required = true) Integer modularId, String kw) {
        if (modularId == 0) {
            // 首页更多精选商家
            Wrapper<Shop> wrapper = new EntityWrapper<Shop>()
                // 是否可用  0不可用 1可用
                .eq(Shop.CAN_USE, 1)
                // 是否推荐,0=不推荐,1=推荐
                .eq(Shop.IS_RECOMMEND, 1)
                .eq(Shop.MODULAR_ID, modularId)
                .orderBy(Shop.TOTAL_VOLUME, false);
            if (StrUtil.isNotBlank(kw)) {
                wrapper.like(Shop.SHOP_NAME, "%" + kw + "%");
            }
            Page shopPage = shopService.selectPage(page, wrapper);
            return new Result().success(shopPage);
        } else {
            // 更多商家列表
            Wrapper<Shop> wrapper = new EntityWrapper<Shop>()
                .eq(Shop.CAN_USE, 1)
                .eq(Shop.MODULAR_ID, modularId)
                .orderBy(Shop.TOTAL_VOLUME, false);
            if (StrUtil.isNotBlank(kw)) {
                wrapper.like(Shop.SHOP_NAME, "%" + kw + "%");
            }
            Page shopPage = shopService.selectPage(page,
                wrapper);
            return new Result().success(shopPage);
        }
    }

    @ApiOperation(value = "用户端-根据模块ID获取更多商品")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数",
            paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/shopGoodsListByModularId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result shopGoodsListByModularId(Page page, @ApiParam(value = "模块ID", required = true) Integer modularId) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsSizeVoList = shopGoodsService.shopGoodsListByModularId(page, ew, modularId);
        return new Result().success(goodsSizeVoList);
    }

    @ApiOperation(value = "商家端-获取分类列表")
    @RequestMapping(value = "/getModularList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getModularList() {
        List<ModularPortalVo> modularPortalVoList = modularPortalService.getModularList();
        for (int i = 0; i < modularPortalVoList.size(); i++) {
            if (modularPortalVoList.get(i).getName().equals("便捷生活")) {
                modularPortalVoList.remove(i);
            }
        }
        return new Result().success(modularPortalVoList);
    }

}

