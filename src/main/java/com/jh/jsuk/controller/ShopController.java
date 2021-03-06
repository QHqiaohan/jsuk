package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.ShopAttributeVo;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.Date2;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
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
    private ShopSetService shopSetService;//商家设置是否包邮
    @Autowired
    private ShopAttributeGoodsService shopAttributeGoodsService;
    @Autowired
    private ShopTodayStatisticsService shopTodayStatisticsService;
    @Autowired
    private ShopMonthStatisticsService shopMonthStatisticsService;
    @Autowired
    private UserRemainderService userRemainderService;
    @Autowired
    private ManagerUserService managerUserService;


    @PatchMapping
    public R edit(Shop shop) {
        shop.updateById();
        return R.succ();
    }

    /**
     * 修改商家的设置是否包邮和使用积分
     * @param se
     * @return
     */
    @PostMapping(value = "/setShopSet")
    public Result setShopSet(ShopSets se) {
        boolean b = se.updateById();
        if (b) {
            return new Result().success("修改成功");
        }
        return new Result().success("修改失败");
    }

    /**
     * 商家获取是否包邮
     *
     * @param shopId
     * @return
     */
    @GetMapping("/getShopSetByShopId")
    public Result getShopSetByShopId(Integer shopId) {
        if (shopId == null) {
            return new Result().erro("数据为空");
        }

        ShopSets sd = shopSetService.getShopSetByShopId(shopId);//根据店铺id查询一条信息
        //如果为空则添加一条新数据，否则就将查询的数据返回前台
        if (sd == null) {
            ShopSets ss = new ShopSets();
            ss.setIntegral(1);//设置使用积分为否
            ss.setMoney(0.0);//设置包邮金额为0；
            ss.setPackagemail(1);//设置是否包邮为0 ；
            ss.setShopid(shopId);
            ss.insert();//添加新数据

            ShopSets shopid = shopSetService.getShopSetByShopId(shopId);//根据店铺id查询一条信息

            return new Result().success(shopid);
        }
        return new Result().success(sd);
    }

    @GetMapping("/list")
    public R list() {
        Wrapper<Shop> wrapper = new EntityWrapper<>();
        wrapper.eq(Shop.CAN_USE, 1);
        return R.succ(shopService.selectList(wrapper));
    }

    @Autowired
    ShopVisitService shopVisitService;

    @ApiOperation("用户端-根据店铺id查看店铺信息")
    @RequestMapping(value = "/getShopById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopById(@ApiParam(value = "店铺id", required = true) Integer shopId, Integer userId) {
        ShopTelPhoneVo shop = shopService.getShopById(shopId);
        if (shop == null) {
            return new Result().success(null);
        }
        EntityWrapper<ShopVisit> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopVisit.SHOP_ID, shopId);
        wrapper.between(ShopVisit.PUBLISH_TIME, new Date2().start(), new Date2().end());
        List<ShopVisit> visits = shopVisitService.selectList(wrapper);
        ShopVisit visit = null;
        for (ShopVisit v : visits) {
            if (userId.equals(v.getUserId())) {
                visit = v;
            }
        }
        if (visit != null) {
            visit.setPublishTime(new Date());
            visit.updateById();
            return new Result().success(shop);
        }
        // 总访问量+1
        Shop shop1 = shopService.selectById(shopId);
        shop1.setTotalVolume(shop1.getTotalVolume() + 1);
        shop1.updateById();

        // 今日访客+1
        ShopTodayStatistics todayStatistics = shopTodayStatisticsService.selectOne(new EntityWrapper<ShopTodayStatistics>()
            .eq(ShopTodayStatistics.SHOP_ID, shopId));

        if (todayStatistics == null) {
            ShopTodayStatistics shopTodayStatistics = new ShopTodayStatistics();
            shopTodayStatistics.setShopId(shopId);
            shopTodayStatistics.insert();
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
            ShopMonthStatistics shopMonthStatistics = new ShopMonthStatistics();
            shopMonthStatistics.setShopId(shopId);
            shopMonthStatistics.insert();
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

    @ApiOperation("用户端-根据店铺id查看店铺内部的商品分类-属性")
    @RequestMapping(value = "/getShopAttributeByShopId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopAttributeByShopId(@ApiParam(value = "店铺id", required = true) Integer shopId) {
        List<ShopAttributeVo> attributeVo = shopAttributeGoodsService.getShopAttributeByShopId(shopId);
        return new Result().success(attributeVo);
    }

    @ApiOperation("用户端-显示收藏的店铺列表")
    @RequestMapping(value = "/collect", method = {RequestMethod.POST, RequestMethod.GET})
    public Result list(Integer userId) {
        List<Shop> shops = shopService.findCollectByUserId(userId);
        return new Result().success(shops);
    }
    @Autowired
    private UserAddressService userAddressService;

    @ApiOperation("商家端-店铺管理")
    @RequestMapping(value = "/addShopInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addShopInfo(@ApiParam(value = "商家id") Integer userId, @ModelAttribute Shop shop) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if (managerUser == null) {
            return new Result().erro("系统错误,请稍后再试");
        }

        Integer shopId = managerUser.getShopId();
        shop.setId(shopId);


        Shop s = new Shop();
        Shop shop1 = s.selectById(shopId);
        UserAddress ua1;
        if(shop1!=null){
            if(shop1.getAddressId()==null){
                UserAddress ua = new UserAddress();
                ua.setLatitude(shop1.getLatitude()+"");//设置维度
                ua.setLongitude(shop1.getLongitude()+"");//设置经度
                ua.setName(shop1.getShopName());//设置名称
                ua.setPhone(shop1.getShopPhone());//设置手机
                ua.setAddress(shop1.getAddress());
                ua.insert();
               ua1 = userAddressService.selectOne(new EntityWrapper<UserAddress>()
                    .eq(UserAddress.PHONE, shop1.getShopPhone())
                    .eq(UserAddress.NAME,shop1.getShopName()));
               if(ua1!=null){
                   shop.setAddressId(ua1.getId());
               }
            }else{
                UserAddress ud = new UserAddress();
                UserAddress userAddress = ud.selectById(shop1.getAddressId());
                userAddress.setAddress(shop1.getAddress());
                userAddress.setLongitude(shop1.getLongitude()+"");
                userAddress.setLatitude(shop1.getLatitude()+"");
                userAddress.setName(shop1.getShopName());
                userAddress.updateById();
            }

        }
        shop.updateById();
        return new Result().success("店铺信息修改成功");
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
    @ApiIgnore
    @PostMapping("/ui/edit")
    public Result uiEdit(HttpSession session, @ModelAttribute Shop shop) {
        Integer adminRole = Integer.parseInt(session.getAttribute("adminRole").toString());
        if (adminRole == 2) {
            return new Result().erro("权限不足，请切换账号");
        }
        shopService.updateById(shop);
        return new Result().success();
    }

    @ApiIgnore
    @RequestMapping("/ui/all")
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
    @RequestMapping(value = "/pay", method = {RequestMethod.POST, RequestMethod.GET})
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

