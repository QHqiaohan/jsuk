package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.Dictionary;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.SensitiveWordUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 发布活动相关表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"首页相关API--便捷生活新/乡村旅游/二手市场部分API"})
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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private MarketCommentService marketCommentService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private ActivityJoinService activityJoinService;

    @ApiOperation(value = "获取首页相关信息-上部分")
    @RequestMapping(value = "/getAll", method = {RequestMethod.POST, RequestMethod.GET})
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
                new Page<>(1, 4),
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
    @RequestMapping(value = "/getAllBelow", method = {RequestMethod.POST, RequestMethod.GET})
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
    @RequestMapping(value = "/getNiceChoose", method = {RequestMethod.POST, RequestMethod.GET})
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
                    new Page<>(1, 4),
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
                    new Page<>(1, 4),
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
                    new Page<>(1, 4),
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
    @RequestMapping(value = "/getMoreInfo", method = {RequestMethod.POST, RequestMethod.GET})
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
    @RequestMapping(value = "/getVipShop", method = {RequestMethod.POST, RequestMethod.GET})
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
    @RequestMapping(value = "/getIsRecommend", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getIsRecommend(Page page) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getIsRecommend(page, ew);
        return new Result().success(goodsPage);
    }

    @ApiOperation(value = "二手市场-获取banner/商品列表/留言总数", notes = "isSecondaryMarket:0=未开启二手市场,1=开启")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/getToMarket", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getToMarket(Page page, Integer userId) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        // 查询该用户是否开启二手市场
        User user = userService.selectById(userId);
        Integer isSecondaryMarket = user.getIsSecondaryMarket();
        map.put("isSecondaryMarket", isSecondaryMarket);
        /**
         * 获取banner
         */
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                // 5=二手市场
                .eq(Banner.BANNER_LOCATION, 5)
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.SORT, false));
        map.put("banner", bannerList);
        /**
         * 获取二手市场商品列表
         */
        MyEntityWrapper<User> ew = new MyEntityWrapper<>();
        Page activityList = activityService.getActivityList(page, ew, userId);
        map.put("activity", activityList);
        return new Result().success(map);
    }

    @ApiOperation("二手市场-商品详细信息")
    @RequestMapping(value = "/toMarketInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public Result toMarketInfo(@ApiParam(value = "商品ID", required = true) Integer id) {
        ActivityVo activityVo = activityService.findActivity(id);
        return new Result().success(activityVo);
    }

    @ApiOperation("二手市场&便捷生活-查询我发布的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")})
    @RequestMapping(value = "/findMyActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result findMyActivity(Page page, Integer userId, @ApiParam(value = "1=乡村旅游,2=便捷生活,3=二手市场", required = true) Integer type) {
        Page myInfoPage = activityService.selectPage(page, new EntityWrapper<Activity>()
                .eq(Activity.IS_DEL, 0)
                .eq(Activity.TYPE, type)
                .eq(Activity.USER_ID, userId)
                .orderBy(Activity.PUBLISH_TIME, false));
        if (CollectionUtils.isEmpty(myInfoPage.getRecords())) {
            return new Result().success("活动列表为空");
        } else {
            return new Result().success(myInfoPage);
        }
    }

    @ApiOperation("二手市场&便捷生活-根据ID删除活动")
    @RequestMapping(value = "/delActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delActivity(Integer userId, @ApiParam(value = "活动ID", required = true) Integer activityId) {
        Activity activity = new Activity();
        // 0=删除
        activity.setIsDel(1);
        boolean res = activity.update(new EntityWrapper<Activity>()
                .eq(Activity.USER_ID, userId)
                .eq(Activity.ID, activityId));
        if (res) {
            return new Result().success();
        } else {
            return new Result().erro("删除失败,请稍后再试");
        }
    }

    @ApiOperation("二手市场&便捷生活-编辑我发布的活动")
    @RequestMapping(value = "/updateActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result updateActivity(@ModelAttribute Activity activity) {
        if (activity == null) {
            return new Result().erro("必填数据为空");
        } else {
            boolean res = activity.insert();
            if (res) {
                return new Result().success();
            } else {
                return new Result().erro("编辑失败");
            }
        }
    }

    @ApiOperation("二手市场&便捷生活-根据商品ID获取留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getMarketCommentById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getMarketCommentById(@ApiParam(value = "商品ID", required = true) Integer activityId, Page page) {
        MyEntityWrapper<User> ew = new MyEntityWrapper<>();
        Page commentPage = marketCommentService.findCommentByActivityId(page, ew, activityId);
        return new Result().success(commentPage);
    }

    @ApiOperation(value = "二手市场&便捷生活-发表/回复留言", notes = "comment_id为空=新留言,不为空=回复")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "comment", value = "留言内容",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "activityId", value = "活动ID",
                    required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "commentId", value = "留言ID",
                    paramType = "query", dataType = "Integer"),
    })
    @RequestMapping(value = "/addComment", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addComment(MarketComment marketComment) {
        //获取敏感词
        Dictionary dictionary = dictionaryService.selectOne(new EntityWrapper<Dictionary>().eq("code", "sensitive_words"));
        String sensitiveWord = dictionary.getValue();
        // 分割
        String[] sensitiveWords = sensitiveWord.split("、");
        Set<String> sensitiveWordSet = new HashSet<>();
        for (String str : sensitiveWords) {
            // 去除重复敏感词
            sensitiveWordSet.add(str);
        }
        SensitiveWordUtil.init(sensitiveWordSet);
        try {
            // 用*替换敏感词
            String content = SensitiveWordUtil.replaceSensitiveWord(marketComment.getComment(), '*');
            marketComment.setComment(content);
            // comment_id为空=新评论,不为空=回复
            if (marketComment.getCommentId() == null) {
                boolean res = marketComment.insert();
                if (res) {
                    return new Result().success("发表留言成功!");
                } else {
                    return new Result().erro("发表留言失败,请稍后再试", res);
                }
            } else {
                boolean res = marketComment.insert();
                if (res) {
                    return new Result().success("回复留言成功!");
                } else {
                    return new Result().erro("回复留言失败,请稍后再试", res);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result().erro("新增失败", e);
        }
    }

    @ApiOperation(value = "便捷生活&二手市场&乡村旅游-新增活动",
            notes = "type按类型必填!!! 1=乡村旅游,2=便捷生活,3=二手市场',如果是便捷生活,classId必填!!如果是乡村旅游,modularId必填!!二手市场不用填")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@ModelAttribute Activity activity) {
        boolean res = activity.insert();
        if (res) {
            return new Result().success("发布成功!");
        } else {
            return new Result().erro("发布失败,服务器繁忙,请稍后再试");
        }
    }

    @ApiOperation("乡村旅游-参与活动")
    @RequestMapping(value = "/join", method = {RequestMethod.POST, RequestMethod.GET})
    public Result join(Integer activityId, Integer userId) {
        ActivityJoin activityJoin = new ActivityJoin();
        activityJoin.setActivityId(activityId);
        activityJoin.setUserId(userId);
        activityJoin.insert();
        return new Result().success("参与成功!");
    }

    @ApiOperation("乡村旅游-根据状态查询我的活动")
    @RequestMapping(value = "/getInfoByStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getInfoByStatus(@ApiParam(name = "0=待付款,1=进行中,2=完成", required = true) Integer status, Integer userId) {
        // 封装活动信息list
        List<Object> resList = new ArrayList<>();
        List<ActivityJoin> activityJoinList = activityJoinService.selectList(new EntityWrapper<ActivityJoin>()
                .eq(ActivityJoin.STATUS, status)
                .eq(ActivityJoin.USER_ID, userId));
        // 获取活动ID
        for (ActivityJoin activityJoin : activityJoinList) {
            Integer activityId = activityJoin.getActivityId();
            // 根据活动ID查询活动信息
            List<Activity> activityList = activityService.selectList(new EntityWrapper<Activity>()
                    .eq(Activity.ID, activityId)
                    .eq(Activity.IS_DEL, 1)
                    .orderBy(Activity.PUBLISH_TIME, false));
            resList.add(activityList);
        }
        if (resList.size() == 0) {
            return new Result().erro("该用户没有活动信息");
        } else {
            return new Result().success(resList);
        }
    }

}

