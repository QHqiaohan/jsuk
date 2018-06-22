package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 发布活动相关表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ModularPortalService modularPortalService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ExpressNewsService expressNewsService;
    @Autowired
    private ActivitySmallService activitySmallService;
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "获取首页相关信息-上部分")
    @PostMapping("/getAll")
    public Result getAll() {
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        /**
         * 首页banner
         */
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                // 0=首页
                .eq(Banner.BANNER_LOCATION, 0)
                // 1=有效
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.SORT, false));
        if (CollectionUtils.isEmpty(bannerList)) {
            map.put("banner", "暂无数据");
        } else {
            map.put("banner", bannerList);
        }
        /**
         * 模块分类
         */
        Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                new Page<>(1, 10),
                new EntityWrapper<ModularPortal>()
                        // 父菜单
                        .eq(ModularPortal.PARENT_ID, 0)
                        // 1=有效
                        .eq(ModularPortal.STATUS, 1)
                        .orderBy(ModularPortal.SORT_ORDER, false));
        map.put("modular", modularPortalPage.getRecords());
        /**
         * 快报
         */
        Page<ExpressNews> expressNewsPage = expressNewsService.selectPage(
                new Page<>(1, 1),
                new EntityWrapper<ExpressNews>()
                        // 1=首页
                        .eq(ExpressNews.TYPE, 1)
                        // 0=未删除
                        .eq(ExpressNews.IS_DEL, 0)
                        .orderBy(ExpressNews.PUBLISH_TIME, false));
        if (CollectionUtils.isEmpty(bannerList)) {
            map.put("news", "暂无数据");
        } else {
            map.put("news", expressNewsPage.getRecords());
        }
        /**
         * 活动模块
         */
        Page<ActivitySmall> activitySmallPage = activitySmallService.selectPage(
                new Page<>(1, 2),
                new EntityWrapper<ActivitySmall>()
                        .eq(ActivitySmall.IS_DEL, 1)
                        .eq(ActivitySmall.STATUS, 1)
                        .orderBy(ActivitySmall.RANK, false));
        if (CollectionUtils.isEmpty(bannerList)) {
            map.put("activity", "暂无数据");
        } else {
            map.put("activity", activitySmallPage.getRecords());
        }
        return new Result().success(map);
    }

    @ApiOperation(value = "获取首页相关信息-下部分")
    @PostMapping("/getAllBelow")
    public Result getAllBelow() {
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        /**
         * 商品推荐
         */
        List<GoodsSalesPriceVo> goodsSalesPriceVos = shopGoodsService.findShopGoodsByModularId(0);
        if (CollectionUtils.isEmpty(goodsSalesPriceVos)) {
            map.put("shopGoods", "暂无数据");
        } else {
            map.put("shopGoods", goodsSalesPriceVos);
        }
        /**
         * 精选商家
         */
        Page<Shop> shopPage = shopService.selectPage(
                new Page<>(1, 8),
                new EntityWrapper<Shop>()
                        .eq(Shop.CAN_USE, 1)
                        .eq(Shop.IS_RECOMMEND, 1)
                        .eq(Shop.MODULAR_ID, 0)
                        .orderBy(Shop.TOTAL_VOLUME, false));
        map.put("shop", shopPage.getRecords());
        /**
         * 专题精选
         */
       /* Page<SpecialTheme> themePage = specialThemeService.selectPage(
                new Page<>(1, 1),
                new EntityWrapper<SpecialTheme>()
                        .eq(SpecialTheme.IS_DEL, 0)
                        .orderBy(SpecialTheme.RANK, false));
        map.put("specialTheme", themePage.getRecords().size() > 0 ? themePage.getRecords().get(0) : null);*/
        return new Result().success(map);
    }

}

