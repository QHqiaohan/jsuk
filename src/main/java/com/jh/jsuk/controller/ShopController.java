package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShopMonthStatistics;
import com.jh.jsuk.entity.ShopTodayStatistics;
import com.jh.jsuk.entity.ShopVisit;
import com.jh.jsuk.entity.vo.ShopAttributeVo;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.entity.vo.ShopVisitorVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-05-24
 */
@Api(tags = "商家店铺相关API:")
@RestController
@RequestMapping("/shop")
public class ShopController {

    protected static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private ShopAttributeGoodsService shopAttributeGoodsService;
    @Autowired
    private ShopTodayStatisticsService shopTodayStatisticsService;
    @Autowired
    private ShopMonthStatisticsService shopMonthStatisticsService;
    @Autowired
    private ShopVisitService shopVisitService;
    @Autowired
    private ShopTodayMoneyService shopTodayMoneyService;
    @Autowired
    private UserRemainderService userRemainderService;

    @ApiOperation("用户端-根据店铺id查看店铺信息")
    @GetMapping("/getShopById")
    public Result getShopById(@ApiParam(value = "店铺id", required = true) Integer shopId, Integer userId) {
        ShopTelPhoneVo shop = shopService.getShopById(shopId);
        if (shop == null) {
            return new Result().success(null);
        }
        // 总访问量+1
        Shop shop1 = shopService.selectById(shopId);
        shop1.setTotalVolume(shop1.getTotalVolume() + 1);
        shop1.updateById();
        // 今日访客+1
        ShopTodayStatistics todayStatistics = shopTodayStatisticsService.selectOne(new EntityWrapper<ShopTodayStatistics>()
                .eq(ShopTodayStatistics.SHOP_ID, shopId));
        if (todayStatistics == null) {
            todayStatistics.setShopId(shopId);
            todayStatistics.insert();
        } else {
            Integer todayVisitor = todayStatistics.getTodayVisitor();
            todayVisitor++;
            todayStatistics.setTodayVisitor(todayVisitor);
            todayStatistics.updateById();
        }
        // 月访问人数+1
        ShopMonthStatistics monthStatistics = shopMonthStatisticsService.selectOne(new EntityWrapper<ShopMonthStatistics>()
                .eq(ShopMonthStatistics.SHOP_ID, shopId));
        if (monthStatistics == null) {
            monthStatistics.setShopId(shopId);
            monthStatistics.insert();
        } else {
            Integer monthVisitor = monthStatistics.getMonthVisitor();
            monthVisitor++;
            monthStatistics.setMonthVisitor(monthVisitor);
            monthStatistics.updateById();
        }
        // 访问记录
        ShopVisit shopVisit = new ShopVisit();
        shopVisit.setUserId(userId);
        shopVisit.setShopId(shopId);
        shopVisit.insert();

        return new Result().success(shop);
    }

    @ApiOperation("商家端-查看今日访客列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "today", value = "当前日期", required = true, paramType = "query", dataType = "string")
    })
    @GetMapping("/getTodayVisit")
    public Result getTodayVisit(Page page, Integer shopId, String today) {
        MyEntityWrapper<ShopVisitorVo> ew = new MyEntityWrapper<>();
        Page visitList = shopVisitService.getVisitList(page, ew, shopId, today);
        if (CollectionUtils.isEmpty(visitList.getRecords())) {
            return new Result().success("暂无数据", null);
        } else {
            return new Result().success(visitList);
        }
    }

    @ApiOperation("商家端-查看今日交易额列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "today", value = "当前日期", required = true, paramType = "query", dataType = "string")
    })
    @PostMapping("/getTodayMoney")
    public Result getTodayMoney(Page page, Integer shopId, String today) {
        MyEntityWrapper<ShopVisitorVo> ew = new MyEntityWrapper<>();
        Page moneyList = shopTodayMoneyService.getTodayMoneyList(page, ew, shopId, today);
        if (CollectionUtils.isEmpty(moneyList.getRecords())) {
            return new Result().success("暂无数据", null);
        } else {
            return new Result().success(moneyList);
        }
    }

    @ApiOperation("用户端-根据店铺id查看店铺内部的商品分类-属性")
    @GetMapping("/getShopAttributeByShopId")
    public Result getShopAttributeByShopId(@ApiParam(value = "店铺id", required = true) Integer shopId) {
        ShopAttributeVo attributeVo = shopAttributeGoodsService.getShopAttributeByShopId(shopId);
        if (attributeVo == null) {
            return new Result().success("暂无数据", null);
        } else {
            return new Result().success(attributeVo);
        }
    }

    @ApiOperation("用户端-显示收藏的店铺列表")
    @GetMapping("/collect")
    public Result list(Integer userId) {
        List<Shop> shops = shopService.findCollectByUserId(userId);
        if (shops == null) {
            return new Result().success("暂无数据", null);
        } else {
            return new Result().success(shops);
        }
    }

/*    @ApiOperation("用户端-搜索店铺列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "cityId", value = "城市id",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "areaId", value = "区县id",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "longitude",
                    required = false, value = "经度", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "latitude",
                    required = false, value = "纬度", paramType = "query", dataType = "double"),
    })
    @PostMapping("/list")
    public Result list(@ApiParam(hidden = true) Page page,
                       @RequestParam(required = false) Integer cityId,
                       @RequestParam(required = false) Integer areaId,
                       @RequestParam(required = false) @ApiParam(value = "标签ID") Integer goodsLabelId,
                       @RequestParam(required = false) @ApiParam(value = "店铺或商品名称") String name,
                       @RequestParam(required = false, defaultValue = "0") @ApiParam("智能排序 0降序 1升序") Integer rank,
                       @RequestParam(required = false) Double longitude,
                       @RequestParam(required = false) Double latitude) {
        boolean order = false;
        if (rank != 0) {
            order = true;
        }
        page.setSize(100);
        List<ShopVo> shopList = shopService.findByList(page, cityId, areaId, goodsLabelId, name, order);
        if (shopList != null && shopList.size() > 0) {
            for (ShopVo shopVo : shopList) {
                shopVo.setDistance(longitude, latitude);
            }
            //根据距离排序
            //Collections.sort(shopList, new DistanceComparator());
        }
        return new Result().success(shopList);
    }*/

    @PostMapping("/ui/edit")
    public Result uiEdit(HttpSession session, @ModelAttribute Shop shop) {
        Integer adminRole = Integer.parseInt(session.getAttribute("adminRole").toString());
        if (adminRole == 2) {
            return new Result().erro("权限不足，请切换账号");
        }
        shopService.updateById(shop);
        return new Result().success();
    }

    @GetMapping("/ui/all")
    public Result all() {
        List<Shop> shops = shopService.selectList(null);
        return new Result().success(shops);
    }

/*    @PostMapping("/ui/addShopForAdmin")
    public Result uiAddShopForAdmin(HttpSession session) {
        Integer adminRole = Integer.parseInt(session.getAttribute("adminRole").toString());
        if (adminRole == 2) {
            return new Result().erro("权限不足，请切换账号");
        }
        Shop shop = new Shop();

        shop.setStartTime(Time.valueOf("10:00:00"));
        shop.setEndTime(Time.valueOf("21:00:00"));
        shop.setCanUse(1);
        shop.insert();
        ShopUser shopUser = shopUserService.selectById(shop.getId());
        shopUser.setShopId(shop.getId());
        shopUser.updateById();
        return new Result().success().setMsg("添加成功!");
    }

    @PostMapping("/ui/tuijian")
    @Transactional
    public Result tuijian(Integer shopId, Integer tjId) {
        return new Result().success();
    }*/

    @ApiOperation("到店支付、扫码支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "shopId", value = "商家id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "amount", value = "交易金额",
                    required = true, paramType = "query", dataType = "string")
    })
    @PostMapping("/pay")
    public Result pay(Integer shopId, Integer userId, String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        Result<Object> result = new Result<>();
        if (!userRemainderService.hasRemain(userId, bigDecimal)) {
            return result.erro("当前用户余额不足！");
        }
        if (!shopService.isExist(shopId)) {
            return result.erro("商户不存在");
        }
        try {
            shopService.doDeal(shopId, userId, bigDecimal);
        } catch (Exception e) {
            logger.error("交易失败", e);
            return result.erro("交易失败");
        }
        return result.success();
    }

}

