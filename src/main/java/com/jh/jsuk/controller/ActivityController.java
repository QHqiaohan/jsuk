package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.Dictionary;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.entity.vo.ActivityVoT;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.SensitiveWordUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "用户-获取首页相关信息-上部分")
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

    @ApiOperation(value = "用户-获取首页相关信息-下部分")
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
        map.put("shop", shopPage);
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

    @ApiOperation("用户-城乡优购&本地商城&聚鲜U客-获取banner/分类/快报")
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

    @ApiOperation("用户-特色家乡&直销平台-banner/分类/店铺列表/商品列表")
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
            /**
             * 店铺列表
             */
            List<Shop> shopList = shopService.findShopsByUserArea(null);
            map.put("shops", shopList);
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
            /**
             * 店铺列表
             */
            List<Shop> shopList = shopService.findShopsByUserArea(null);
            map.put("shops", shopList);
        }
        /**
         * 商品列表
         */
        List<GoodsSalesPriceVo> shopGoodsByModularId = shopGoodsService.findShopGoodsByModularId(modularId);
        map.put("shopGoods", shopGoodsByModularId);
        return new Result().success(map);
    }

    @ApiOperation("用户-会员商城-获取banner-店铺列表/分类/商品列表")
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
        /**
         * 店铺列表
         */
        List<Shop> shopList = shopService.findShopsByUserArea(null);
        map.put("shops", shopList);
        /**
         * 商品列表
         */
        List<GoodsSalesPriceVo> shopGoodsByModularId = shopGoodsService.findShopGoodsByModularId(modularId);
        map.put("shopGoods", shopGoodsByModularId);
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

    //首页-二手市场，展示banner和活动列表
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
        // 查询该用户是否开启二手市场  1:开启  0:禁用
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
         * 获取二手市场商品(活动)列表
         */
        MyEntityWrapper<User> ew = new MyEntityWrapper<>();
        Page activityList = activityService.getActivityList(page, ew, userId);
        map.put("activity", activityList);
        return new Result().success(map);
    }

    //首页-二手市场-活动列表-点击查看活动详情
    @ApiOperation("二手市场-商品详细信息")
    @RequestMapping(value = "/toMarketInfo", method = {RequestMethod.POST, RequestMethod.GET})
    //接收前台传过来的activityId
    public Result toMarketInfo(@ApiParam(value = "商品ID", required = true) Integer id) {
        ActivityVo activityVo = activityService.findActivity(id);
        return new Result().success(activityVo);
    }


    //首页-二手市场-点击右上角查询我发布的二手市场列表,如果查询我发布的二手市场列表,前台传type=3
    //首页-便捷生活-点击右上角查询我发布的便捷生活列表，前台传type=2
    @ApiOperation("用户-二手市场&便捷生活-查询我发布的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer")})
    @RequestMapping(value = "/findMyActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result findMyActivity(Page page,
                                 @ApiParam(value="用户id",required=true) Integer userId,
                                 //@ApiParam(value = "1=乡村旅游,2=便捷生活,3=二手市场", required = true)
                                 @RequestParam Integer modularId) {
        Page myInfoPage = activityService.selectPage(page, new EntityWrapper<Activity>()
                .eq(Activity.IS_DEL, 0)
                .eq(Activity.MODULAR_ID, modularId)
                .eq(Activity.USER_ID, userId)
                .orderBy(Activity.PUBLISH_TIME, false));
        if (CollectionUtils.isEmpty(myInfoPage.getRecords())) {
            return new Result().success("活动列表为空");
        } else {
            return new Result().success(myInfoPage);
        }
    }

    //首页-二手市场-我发表的二手市场列表-删除
    //首页-便捷生活-我发表的便捷生活列表-删除
    @ApiOperation("用户-二手市场&便捷生活-根据ID删除活动")
    @RequestMapping(value = "/delActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delActivity(Integer userId, @ApiParam(value = "活动ID", required = true) Integer activityId) {
        Activity activity = new Activity();
        // 0=未删除，1=删除
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

    //首页-二手市场-我发布的二手市场列表-编辑
    //首页-便捷生活-我发布的便捷生活列表-编辑
    @ApiOperation("二手市场&便捷生活-编辑我发布的活动")
    @RequestMapping(value = "/updateActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public Result updateActivity(@ModelAttribute Activity activity) {
        if (activity == null) {
            return new Result().erro("必填数据为空");
        } else {
            boolean res = activity.updateById();
            if (res) {
                return new Result().success();
            } else {
                return new Result().erro("编辑失败");
            }
        }
    }


    //用户-首页-二手市场-查看活动详情-根据活动id获取留言
    //用户-首页-便捷生活-查看活动详情-根据活动id获取留言
    @ApiOperation("用户-二手市场&便捷生活-根据商品ID获取留言")
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


    //首页-二手市场-活动列表-活动详情-发表/回复留言
    //首页-便捷生活-活动列表-活动详情-留言/回复留言
    @ApiOperation(value = "用户-二手市场&便捷生活-发表/回复留言", notes = "comment_id为空=新留言,不为空=回复")
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
            if (marketComment.getCommentId() == null) {     //发表评论
                boolean res = marketComment.insert();
                if (res) {
                    return new Result().success("发表留言成功!");
                } else {
                    return new Result().erro("发表留言失败,请稍后再试", res);
                }
            } else {
                /*
                回复留言
                前端需要把回复的评论对象id作为传过来的marketComment的comment_id
                 */
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

    //乡村旅游现在不需要新增活动的功能
    //首页-二手市场-发布活动
    //首页-便捷生活-发布活动
    @ApiOperation(value = "用户-便捷生活&二手市场&乡村旅游-新增活动",
            notes = "type按类型必填!!! 1=乡村旅游,2=便捷生活,3=二手市场',如果是便捷生活,classId必填!!如果是乡村旅游,modularId必填!!二手市场不用填." +
                "activityType:0代表普通活动,1代表共享婚车活动;status:1代表商家,2代表需求")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public Result add(@ModelAttribute Activity activity,
                      @RequestParam Integer modularId,
                      @RequestParam Integer activityType,
                      @RequestParam Integer status) {
        activity.setActivityType(activityType);
        activity.setStatus(status);
        activity.setModularId(modularId);
        boolean res = activity.insert();
        if (res) {
            return new Result().success("发布成功!");
        } else {
            return new Result().erro("发布失败,服务器繁忙,请稍后再试");
        }
    }


    //用户-乡村旅游-乡村旅游banner
    @ApiOperation("用户-乡村旅游-乡村旅游banner")
    @RequestMapping(value="/getVillageBanner",method = {RequestMethod.POST,RequestMethod.GET})
    public Result getVillageBanner(){
        Result result=new Result();

        List<Banner> villageBannerList=bannerService.selectList(new EntityWrapper<Banner>()
                                     .eq(Banner.BANNER_LOCATION,4)    // 4:乡村旅游
                                     .eq(Banner.IS_VALID,1)          //  是否有效 0:无效 1:有效
                                     .orderBy(Banner.SORT,false)     //    排序 数字越大越靠前
        );
        return result.success(villageBannerList);
    }


    //用户-乡村旅游-关键字搜索
    @ApiOperation("用户-乡村旅游-关键字搜索")
    @RequestMapping(value="/searchActivityByKeywords",method={RequestMethod.GET,RequestMethod.POST})
    public Result searchActivityByKeywords(@RequestParam String keywords){
        Result result=new Result();
        if(keywords!=null && !keywords.equals("")){
            keywords.trim();
        }
        EntityWrapper ew=new EntityWrapper();
        ew.setEntity(new Activity());
        ew.like("title",keywords, SqlLike.DEFAULT);
        List<Activity> searchList=activityService.selectList(ew);

        if(searchList==null || searchList.size()==0){
           return result.erro("抱歉,暂时还没有发布相关活动...");
        }
        return result.success(searchList);
    }


    //根据首页父模块id获取子模块
    @ApiOperation("用户-乡村旅游-乡村旅游子模块")
    @RequestMapping(value="/getChildModularByParentId", method={RequestMethod.GET,RequestMethod.POST})
    public Result getChildModularByParentId(@RequestParam Integer id){
        Result result=new Result();
        List<ModularPortal> childModularList=modularPortalService.selectList(new EntityWrapper<ModularPortal>()
                                                                            .eq(ModularPortal.PARENT_ID,id)
        );
        //List<ModularPortal> list=modularPortalService.findChildListByParentId(id);
        return result.success(childModularList);
    }


    //用户-乡村旅游-热门推荐
    @ApiOperation("用户-乡村旅游-热门推荐")
    @RequestMapping(value="/getHotActivityList",method={RequestMethod.POST,RequestMethod.GET})
    public Result getHotActivityList( Integer current, Integer size, @RequestParam Integer ModularId){
        current=current==null?1:current;
        size=size==null?10:size;
        Result result=new Result();
        Page<Activity> activityPage = activityService.selectPage(new Page<Activity>(current,size),
                                                                 new EntityWrapper<Activity>()
                                                                         .eq(Activity.IS_DEL,0)
                                                                         .eq(Activity.IS_RECOMMEND,1)
                                                                         .eq(Activity.MODULAR_ID,ModularId)
                                                                         .orderBy(Activity.PUBLISH_TIME,false
                                                                         )
        );
        if(activityPage.getRecords()==null || activityPage.getRecords().size()==0){
            return result.erro("亲，暂时没有热门活动哦...");
        }
        return result.success(activityPage);
    }


    //查询我参加的活动列表
    @ApiOperation("用户-乡村旅游-根据状态查询我的活动")
    @RequestMapping(value = "/getInfoByStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getInfoByStatus(//@ApiParam(name = "0=待付款,1=进行中,2=完成", required = true)
                                  @RequestParam Integer status,
                                  @RequestParam Integer userId) {
        // 封装活动信息的list
        Map<String,Activity> activityMap=new HashMap<>();
        List<Activity> activityList=new ArrayList<>();

        //从js_activity_join表中查询我参加的活动列表,显示全部活动列表时status传null
        List<ActivityJoin> activityJoinList=new ArrayList<>();
        if(status!=null) {
            activityJoinList = activityJoinService.selectList(new EntityWrapper<ActivityJoin>()
                    .eq(ActivityJoin.STATUS, status)
                    .eq(ActivityJoin.USER_ID, userId));
        }else{
            activityJoinList = activityJoinService.selectList(new EntityWrapper<ActivityJoin>()
                    .eq(ActivityJoin.USER_ID, userId));
        }

        if(activityJoinList==null || activityJoinList.size()==0){
            return new Result().success();
        }

        // 获取活动ID
        for (ActivityJoin activityJoin : activityJoinList) {
            Integer activityId = activityJoin.getActivityId();
            // 根据活动ID查询活动信息
            Activity activity=activityService.selectOne(new EntityWrapper<Activity>()
                                          .eq(Activity.ID,activityId)
                                           //is_del,是否删除,0:未删除,1:删除
                                          .eq(Activity.IS_DEL,0)
                                          .orderBy(Activity.PUBLISH_TIME,false)
            );
            activityList.add(activity);
        }
        return new Result().success(activityList);
    }

    /**
     * 页面加载方法
     * 根据活动的modular_id连js_modular_portal表查询该活动对应的模块类型(亲子,户外拓展...)
     */
    @ApiOperation("用户-乡村旅游-我的活动列表-页面加载时调用(根据活动的modular_id查询该活动对应的模块类型)")
    @RequestMapping(value="/getMudularName",method = {RequestMethod.POST, RequestMethod.GET})
    public Result getMudularName(@RequestParam Integer modularId){
       ModularPortal modular= modularPortalService.selectById(modularId);
       return new Result().success(modular.getName());
    }

    //用户-乡村旅游-我的活动列表-删除活动
    @ApiOperation("用户-乡村旅游-我的活动列表-删除活动")
    @RequestMapping(value="/deleteActivityById",method = {RequestMethod.POST, RequestMethod.GET})
    public Result deleteActivityById(@RequestParam Integer activityId){
        Result result=new Result();
        try{
            activityJoinService.delete(new EntityWrapper<ActivityJoin>()
                    .eq(ActivityJoin.ACTIVITY_ID,activityId)
            );
            result.success("删除活动成功");
        }catch(Exception e){
            e.printStackTrace();
            result.excption("抱歉!出错啦...我们会尽快解决");
        }

        return result;
    }

    /**
     * 亲子、户外拓展、采摘活动、酒店住宿、特产购买
     * 根据modularId查询模块对应的活动列表
     */
    @ApiOperation("用户-乡村旅游-查询亲子、户外拓展、采摘活动、酒店住宿、特产购买对应活动")
    @RequestMapping(value="/getActivityListByModularId",method = {RequestMethod.POST, RequestMethod.GET})
    public Result getActivityListByModularId(@RequestParam Integer modularId, Integer current, Integer size){
        current=current==null?1:current;
        size=size==null?10:size;

        Page activityPage=activityService.selectPage(new Page(current,size),
                                                     new EntityWrapper<Activity>()
                                                         .eq(Activity.MODULAR_ID,modularId)
                );
        return new Result().success(activityPage);
    }

    /**
     * 根据活动id查询亲子、户外拓展、采摘活动、酒店住宿、特产购买活动详情
     */
    @ApiOperation("用户-乡村旅游-根据活动id查询亲子、户外拓展、采摘活动、酒店住宿、特产购买活动详情")
    @RequestMapping(value="getActivityInfoById",method = {RequestMethod.POST, RequestMethod.GET})
    public Result getActivityInfoById(@RequestParam Integer id){
        Result result=new Result();

        ActivityVoT activityVoT=activityService.selectActivityVoT(id);
        return result.success(activityVoT);
    }

    /**
     * 活动报名
     * 支付定金完成后调用该方法
     */
    @ApiOperation("用户-乡村旅游-活动-活动详情-活动报名(支付定金完成后调用该方法)")
    @RequestMapping(value="/join",method = {RequestMethod.POST, RequestMethod.GET})
    public Result join(Integer activityId,Integer userId, @RequestBody @ApiParam(name="activityJoin对象") ActivityJoin activityJoin){
        Result result=new Result();

        try{
            activityJoin.setActivityId(activityId);
            activityJoin.setUserId(userId);
            activityJoin.setIsDel(0);
            activityJoin.setStatus(1);   //状态 0=待付款,1=进行中,2=完成

            activityJoin.insert();
            return result.success("参与成功!");
        }catch(Exception e){
            e.printStackTrace();
            return result.erro("参加活动失败");
        }
    }

    @ApiOperation("用户端-乡村旅游-根据状态获取活动报名列表")
    @ApiImplicitParams(value={
        @ApiImplicitParam(name="current",value="当前页",paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name="size",value="每页显示条数",paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name="status",value="状态，0=待付款,1=进行中,2=完成",paramType = "query", dataType = "integer")
    })
    @RequestMapping(value="/getActivityJoinByStatus",method={RequestMethod.GET,RequestMethod.POST})
    public Result getActivityJoinByStatus(Integer current,Integer size,Integer status){
        current=current==null?1:current;
        size=size==null?10:size;
        Page<ActivityJoin> activityJoinPage=activityJoinService.selectPage(new Page(current,size),
                                                                           new EntityWrapper<ActivityJoin>()
                                                                               .eq(status!=null,ActivityJoin.STATUS,status)
                                                                               .eq(ActivityJoin.IS_DEL,0)
            );
        return new Result().success(activityJoinPage);
    }

}

