package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
@Api(tags = {"首页模块相关API--便捷生活新/乡村旅游/二手市场部分API"})
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
    @Autowired
    private SpecialThemeService specialThemeService;

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
            map.put("banner", bannerList);
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
            map.put("news", expressNewsPage.getRecords());
        /**
         * 活动模块
         */
        Page<ActivitySmall> activitySmallPage = activitySmallService.selectPage(
                new Page<>(1, 2),
                new EntityWrapper<ActivitySmall>()
                        .eq(ActivitySmall.IS_DEL, 1)
                        .eq(ActivitySmall.STATUS, 1)
                        .orderBy(ActivitySmall.RANK, false));
            map.put("activity", activitySmallPage.getRecords());
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
            map.put("shopGoods", goodsSalesPriceVos);
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
        Page<SpecialTheme> themePage = specialThemeService.selectPage(
                new Page<>(1, 1),
                new EntityWrapper<SpecialTheme>()
                        .eq(SpecialTheme.IS_DEL, 0)
                        .orderBy(SpecialTheme.RANK, false));
        map.put("specialTheme", themePage.getRecords().size() > 0 ? themePage.getRecords().get(0) : null);
        return new Result().success(map);
    }

    @ApiOperation("城乡优购&本地商城&聚鲜U客-获取banner/分类/快报")
    @GetMapping("/getNiceChoose")
    public Result getNiceChoose(@ApiParam(value = "模块ID", required = true) Integer modularId) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        if (modularId == 2) {
            /**
             * 获取banner
             */
            List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                    // 1=城乡优购
                    .eq(Banner.BANNER_LOCATION, 1)
                    .eq(Banner.IS_VALID, 1)
                    .orderBy(Banner.SORT, false));
            map.put("banner", bannerList);
            /**
             * 快报
             */
            Page<ExpressNews> expressNewsPage = expressNewsService.selectPage(
                    new Page<>(1, 1),
                    new EntityWrapper<ExpressNews>()
                            .eq(ExpressNews.IS_DEL, 0)
                            .eq(ExpressNews.TYPE, 2)
                            .orderBy(ExpressNews.PUBLISH_TIME, false));

            map.put("news", expressNewsPage.getRecords());
            /**
             * 分类
             */
            Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                    new Page<>(1, 10),
                    new EntityWrapper<ModularPortal>()
                            .eq(ModularPortal.PARENT_ID, modularId)
                            .eq(ModularPortal.STATUS, 1)
                            .orderBy(ModularPortal.SORT_ORDER, false));
            map.put("modular", modularPortalPage.getRecords());
        } else if (modularId == 4) {
            /**
             * 获取banner
             */
            List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                    // 8=聚鲜U客
                    .eq(Banner.BANNER_LOCATION, 8)
                    .eq(Banner.IS_VALID, 1)
                    .orderBy(Banner.SORT, false));
            map.put("banner", bannerList);
            /**
             * 快报
             */
            Page<ExpressNews> expressNewsPage = expressNewsService.selectPage(
                    new Page<>(1, 1),
                    new EntityWrapper<ExpressNews>()
                            .eq(ExpressNews.IS_DEL, 0)
                            .eq(ExpressNews.TYPE, 3)
                            .orderBy(ExpressNews.PUBLISH_TIME, false));
            map.put("news", expressNewsPage.getRecords());
            /**
             * 分类
             */
            Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                    new Page<>(1, 5),
                    new EntityWrapper<ModularPortal>()
                            .eq(ModularPortal.PARENT_ID, modularId)
                            .eq(ModularPortal.STATUS, 1)
                            .orderBy(ModularPortal.SORT_ORDER, false));
            map.put("modular", modularPortalPage.getRecords());
        } else if (modularId == 6) {
            /**
             * 获取banner
             */
            List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                    // 9=本地商城
                    .eq(Banner.BANNER_LOCATION, 9)
                    .eq(Banner.IS_VALID, 1)
                    .orderBy(Banner.SORT, false));
            map.put("banner", bannerList);
            /**
             * 快报
             */
            Page<ExpressNews> expressNewsPage = expressNewsService.selectPage(
                    new Page<>(1, 1),
                    new EntityWrapper<ExpressNews>()
                            .eq(ExpressNews.IS_DEL, 0)
                            .eq(ExpressNews.TYPE, 4)
                            .orderBy(ExpressNews.PUBLISH_TIME, false));
            map.put("news", expressNewsPage.getRecords());
            /**
             * 分类
             */
            Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                    new Page<>(1, 5),
                    new EntityWrapper<ModularPortal>()
                            .eq(ModularPortal.PARENT_ID, modularId)
                            .eq(ModularPortal.STATUS, 1)
                            .orderBy(ModularPortal.SORT_ORDER, false));
            map.put("modular", modularPortalPage.getRecords());
        }
        return new Result().success(map);
    }

    @ApiOperation("特色家乡&直销平台-banner/分类")
    @GetMapping("/getMoreInfo")
    public Result getMoreInfo(@ApiParam(value = "模块ID", required = true) Integer modularId) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        if (modularId == 3) {
            /**
             * 获取banner
             */
            List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                    // 2=特色家乡
                    .eq(Banner.BANNER_LOCATION, 2)
                    .eq(Banner.IS_VALID, 1)
                    .orderBy(Banner.SORT, false));
            map.put("banner", bannerList);
            /**
             * 分类
             */
            Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                    new Page<>(1, 5),
                    new EntityWrapper<ModularPortal>()
                            .eq(ModularPortal.PARENT_ID, modularId)
                            .eq(ModularPortal.STATUS, 1)
                            .orderBy(ModularPortal.SORT_ORDER));
            map.put("modular", modularPortalPage.getRecords());
        } else if (modularId == 8) {
            /**
             * 获取banner
             */
            List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                    // 10=直销平台
                    .eq(Banner.BANNER_LOCATION, 10)
                    .eq(Banner.IS_VALID, 1)
                    .orderBy(Banner.SORT, false));
            map.put("banner", bannerList);
            /**
             * 分类
             */
            Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                    new Page<>(1, 5),
                    new EntityWrapper<ModularPortal>()
                            .eq(ModularPortal.PARENT_ID, modularId)
                            .eq(ModularPortal.STATUS, 1)
                            .orderBy(ModularPortal.SORT_ORDER));
            map.put("modular", modularPortalPage.getRecords());
        }

        return new Result().success(map);
    }

    @ApiOperation("会员商城-获取banner")
    @GetMapping("/getVipShop")
    public Result getVipShop(@ApiParam(value = "模块ID", required = true) Integer modularId) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取banner
         */
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                // 3=会员商城
                .eq(Banner.BANNER_LOCATION, 3)
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.SORT, false));
        map.put("banner", bannerList);
        /**
         * 分类
         */
        Page<ModularPortal> modularPortalPage = modularPortalService.selectPage(
                new Page<>(1, 5),
                new EntityWrapper<ModularPortal>()
                        .eq(ModularPortal.PARENT_ID, modularId)
                        .eq(ModularPortal.STATUS, 1)
                        .orderBy(ModularPortal.SORT_ORDER));
        map.put("modular", modularPortalPage.getRecords());
        return new Result().success(map);
    }

    @ApiOperation("用户端-首页精品推荐列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")
    })
    @GetMapping("/getIsRecommend")
    public Result getIsRecommend(Page page) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getIsRecommend(page, ew);
        return new Result().success(goodsPage);
    }

}

