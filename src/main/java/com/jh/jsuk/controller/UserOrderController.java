package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.rules.AccountRule;
import com.jh.jsuk.entity.vo.UserOrderInfoVo;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.mq.RobbingOrderProducer;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.*;
import com.jh.jsuk.utils.wx.WxPay;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "订单相关:")
@RestController
@RequestMapping("/userOrder")
public class UserOrderController {

    @Autowired
    Session session;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    UserOrderGoodsService userOrderGoodsService;

    @Autowired
    UserInvitationPayService userInvitationPayService;

    @Autowired
    DistributionUserService distributionUserService;

    @Autowired
    private RuleEngineService<AccountRule> ruleEngineService;

    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;

    @Autowired
    ShopGoodsService shopGoodsService;

    @Autowired
    NewsService newsService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    private ManagerUserService managerUserService;

    @GetMapping("/page")
    public R userOrderPage(Page page, String[] date, String kw, String status) throws Exception {
        OrderStatus orderStatus = null;
        if (status != null && !"all".equals(status)) {
            orderStatus = EnumUitl.toEnum(OrderStatus.class, status, "getShortKey");
        }
        return R.succ(userOrderService.listPage(page, date == null ? null : Arrays.asList(date), kw, orderStatus));
    }

    @GetMapping("/detail")
    public R userOrderDetail(@RequestParam Integer orderId) {
        return R.succ(userOrderService.userOrderDetail(orderId));
    }

    @PostMapping("/close")
    public R userOrderClose(Integer orderId) {
        UserOrder order = new UserOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.CLOSED.getKey());
        order.updateById();
        return R.succ();
    }

    @PostMapping("/confirm")
    public R userOrderConfirm(Integer orderId) {
        UserOrder order = new UserOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.SUCCESS.getKey());
        order.updateById();
        return R.succ();
    }

    @GetMapping("/count")
    public R count() {
        Map<String, Object> map = new HashMap<>();
        OrderStatus[] statuses = {OrderStatus.DUE_PAY, OrderStatus.WAIT_DELIVER, OrderStatus.DELIVERED, OrderStatus.SUCCESS, OrderStatus.CLOSED};
        int all = 0;
        for (OrderStatus status : statuses) {
            int cnt = userOrderService.statusCount(status, session.getShopId());
            all += cnt;
            map.put(status.getShortKey(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    //--------------------骑手端----------------------------------------------//

    /**
     * 待抢单
     */
//    @ApiOperation("骑手-显示待抢单列表")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "current", value = "当前页码",
//                    required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "size", value = "每页条数",
//                    required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "longitude", value = "经度",
//                    required = true, paramType = "query", dataType = "double"),
//            @ApiImplicitParam(name = "latitude", value = "纬度",
//                    required = true, paramType = "query", dataType = "double")
//    })
//    @GetMapping("/grabASingle")
//    public Result grabASingle(Page page, @RequestParam Double longitude, @RequestParam Double latitude) {
//        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
//                .isNull(UserOrder.DISTRIBUTION_USER_ID)
//                .eq(UserOrder.STATUS, 2)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false)
//        );
//        if (orderVoList != null && orderVoList.size() > 0) {
//            for (UserOrderVo orderVo : orderVoList) {
//                orderVo.setDistance(longitude, latitude);
//            }
//            Collections.sort(orderVoList, new DistanceComparator());
//        }
//        return new Result().success(orderVoList);
//    }

    /**
     * 待抢单
     */
//    @ApiOperation("骑手-显示待抢单数量")
//    @GetMapping("/grabASingle/count")
//    public Result grabASingleCount() {
//        Wrapper wrapper = new EntityWrapper()
//                .isNull(UserOrder.DISTRIBUTION_USER_ID)
//                .eq(UserOrder.STATUS, 2)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false);
//        return new Result().success(userOrderService.selectCount(wrapper));
//    }

    @Autowired
    RobbingOrderProducer producer;

//    /**
//     * 配送员抢单
//     */
//    @ApiOperation("骑手抢单")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "orderId", value = "订单Id",
//                    required = true, paramType = "query", dataType = "integer")
//    })
//    @PostMapping("/takingByDistribution")
//    public Result takingByDistribution(@ModelAttribute UserOrderDTO userOrderDTO) {
//        producer.send(userOrderDTO);
//        return new Result().success();
//    }


    /**
     * 待取货
     */
//    @ApiOperation("骑手-显示显示待取货数量")
//    @GetMapping("/readyToTake/count")
//    public Result readyToTake(Integer userId) {
//        Wrapper wrapper = new EntityWrapper()
//                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
//                .eq(UserOrder.STATUS, 3)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false);
//        return new Result().success(userOrderService.selectCount(wrapper));
//    }

    /**
     * 配送员取货
     */
//    @ApiOperation("骑手取货")
//    @PostMapping("/pickUp")
//    public Result pickUp(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
//        UserOrder order = new UserOrder();
//        order.setId(orderId);
//        order.setStatus(4);
//        order.updateById();
//        return new Result().success();
//    }

//    /**
//     * 配送员送达
//     */
//    @ApiOperation("骑手送达")
//    @PostMapping("/service")
//    @Transactional
//    public Result service(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) throws Exception {
//        UserOrder order = userOrderService.selectById(orderId);
//        DistributionUser user = distributionUserService.selectById(order.getDistributionUserId());
//        DistributionDetail detail = new DistributionDetail();
//        detail.setDetail("完成配送");
//        detail.setMoney(order.getDistributionFee());
//
//        AccountRule accountRule = new AccountRule(user, detail);
//        AccountRule result = ruleEngineService.executeRuleEngine("myDetail", accountRule);
//        result.updateUserAndInsertDetail();
//
//        Shop shop = shopService.selectById(order.getShopId());
//
//        //商家添加money
//        shop.setAccountPoint(shop.getAccountPoint().add(order.getOrderPrice()));
//        //shop.addTradingVolume(order.getOrderPrice());
//        shopService.updateById(shop);
//        List<UserOrderGoods> orderGoodsList = userOrderGoodsService.selectList(new EntityWrapper<UserOrderGoods>()
//                .eq(UserOrderGoods.ORDER_ID, orderId));
//
//        String values = "";
//        for (UserOrderGoods goods : orderGoodsList) {
//            values += goods.getGoodsId() + ",";
//        }
//
//        values = values.substring(0, values.length() - 1);
//        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(new EntityWrapper<ShopGoods>()
//                .in(ShopGoods.ID, values));
//        for (ShopGoods shopGoods : shopGoodsList) {
//            shopGoods.setSaleAmont(shopGoods.getSaleAmont() + 1);
//            shopGoods.updateById();
//        }
//
//        order.setCompleteTime(new Date());
//        order.setStatus(5);
//        order.updateById();
//        //赠送优惠券逻辑
//        User userC = userService.selectInfoById(order.getUserId());
//        UserInvitationPay itu = new UserInvitationPay();
//
//        List<UserInvitationPay> list = userInvitationPayService.selectList(new EntityWrapper<UserInvitationPay>().eq(UserInvitationPay.USER_ID,
//                order.getUserId()));
//        if (list != null && !list.isEmpty()) {
//            for (UserInvitationPay userInvitationPay : list) {
//                Integer invitationId = userInvitationPay.getInvitationId();
//                if (invitationId == null)
//                    continue;
//                User userI = userService.selectInfoById(invitationId);
//                List<UserInvitationPay> itus = userInvitationPayService.selectInfoByUser(userC.getId(), invitationId);
//                if (itus.size() == 0) {
//                    itu.setInvitationId(invitationId);
//                    itu.setUserId(userC.getId());
//                    userInvitationPayService.insert(itu);
//                }
//                int userNum = userInvitationPayService.selectInvitationNumByUser(invitationId);
//                int orderNum = userOrderService.selectCount(new MyEntityWrapper<UserOrder>().eq("user_id", userC.getId()));
//                if (orderNum >= 1) {
//                    if (userI.getIsAvailable() != 1 && userNum == 4) {
//                        //创建优惠券
//                        long startTime = System.currentTimeMillis();
//                        Calendar cal = Calendar.getInstance();
//                        cal.add(Calendar.DATE, 30);
//                        Date endTime = cal.getTime();
//                        //发送优惠券
//                        for (int i = 0; i < 10; i++) {
//                            Coupon cp = new Coupon();
//                            cp.setFullPrice(BigDecimal.valueOf(1));
//                            cp.setDiscount(BigDecimal.valueOf(20));
//                            cp.setStartTime(new Date(startTime));
//                            cp.setEndTime(endTime);
//                            cp.setNum(1);
//                            addCoupon(cp);
//                            UserCoupon up = new UserCoupon();
//                            up.setUserId(invitationId);
//                            up.setStatus(1);
//                            up.setCouponId(cp.getId());
//                            up.setPublishTime(new Date(startTime));
//                            sendCouponForUser(up);
//                            cp.setNum(0);
//                            cp.updateById();
//                        }
//                        User uc = new User();
//                        uc.setId(userC.getId());
//                        uc.setIsAvailable(1);
//                        uc.updateById();
//                    }
//                }
//
//            }
//        }


//        if (userC.getInvitationId() != null) {
//            User userI = userService.selectInfoById(userC.getInvitationId());
//            List<UserInvitationPay> itus = userInvitationPayService.selectInfoByUser(userC.getId(), userC.getInvitationId());
//            if (itus.size() == 0) {
//                itu.setInvitationId(userC.getInvitationId());
//                itu.setUserId(userC.getId());
//                userInvitationPayService.insert(itu);
//            }
//            int userNum = userInvitationPayService.selectInvitationNumByUser(userC.getInvitationId());
//            int orderNum = userOrderService.selectCount(new MyEntityWrapper<UserOrder>().eq("user_id", userC.getId()));
//            if (orderNum >= 1) {
//                if (userI.getIsAvailable() != 1 && userNum == 4) {
//                    //创建优惠券
//                    long startTime = System.currentTimeMillis();
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DATE, 30);
//                    Date endTime = cal.getTime();
//                    //发送优惠券
//                    for (int i = 0; i < 10; i++) {
//                        Coupon cp = new Coupon();
//                        cp.setFullPrice(BigDecimal.valueOf(1));
//                        cp.setDiscount(BigDecimal.valueOf(20));
//                        cp.setStartTime(new Date(startTime));
//                        cp.setEndTime(endTime);
//                        cp.setNum(1);
//                        addCoupon(cp);
//                        UserCoupon up = new UserCoupon();
//                        up.setUserId(userC.getInvitationId());
//                        up.setStatus(1);
//                        up.setCouponId(cp.getId());
//                        up.setPublishTime(new Date(startTime));
//                        sendCouponForUser(up);
//                        cp.setNum(0);
//                        cp.updateById();
//                    }
//                    User uc = new User();
//                    uc.setId(userC.getId());
//                    uc.setIsAvailable(1);
//                    uc.updateById();
//                }
//            }
//        }


//        Message message = new Message();
//        message.setMsgType(1);
//        message.setUserId(order.getUserId());
//        message.setRelationId(orderId + "");
//        message.insert();
//        JPushUtils.pushMsg(order.getUserId() + "", "这就是您的菜！妈妈期待您的五星好评~", "", null);
//        News news = new News();
//        news.setNewsType(NewsType.SYS);
//        news.setTitle("");
//        news.setContent("这就是您的菜！妈妈期待您的五星好评~");
//        newsService.push(news, order.getUserId());
//        return new Result().success();
//    }

//    public void addCoupon(Coupon coupon) {
//        coupon.insert();
//    }

//    public void sendCouponForUser(UserCoupon up) {
//        up.insert();
//    }

    /**
     * 待送达
     */
//    @ApiOperation("骑手显示待送达列表")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "current", value = "当前页码",
//                    required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "size", value = "每页条数",
//                    required = false, paramType = "query", dataType = "integer")
//    })
//    @GetMapping("/pendingService")
//    public Result pendingService(Page page, Integer userId) {
//        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
//                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
//                .eq(UserOrder.STATUS, 4)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false)
//        );
//        return new Result().success(orderVoList);
//    }

//    @ApiOperation("骑手显示待送达数量")
//    @GetMapping("/pendingService/count")
//    public Result pendingService(Integer userId) {
//        Wrapper wrapper = new EntityWrapper()
//                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
//                .eq(UserOrder.STATUS, 4)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false);
//        return new Result().success(userOrderService.selectCount(wrapper));
//    }

//    @ApiOperation("骑手显示已完成数量")
//    @GetMapping("/distributionComplete/count")
//    public Result distributionComplete(Integer userId) {
//        Wrapper wrapper = new EntityWrapper()
//                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
//                .eq(UserOrder.STATUS, 5)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false);
//        int i = userOrderService.selectCount(wrapper);
//        return new Result().success(i);
//    }


    //--------------------骑手端----------------------------------------------//
    @ApiOperation(value = "用户端-订单列表&订单关键字模糊搜索", notes = "不传=该用户全部订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "status", value = "0待付款,1待发货,2=已发货 3=交易成功,4=申请退款,5=退款成功,6=交易关闭,7=售后",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getOrderByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderByUserId(Page page, Integer userId, Integer status, String goodsName) {
        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();
        Page orderPage = userOrderService.getOrderByUserId(page, ew, userId, status, goodsName);
        return new Result().success(orderPage);
    }

    @ApiOperation(value = "用户端&商家端-订单详情")
    @RequestMapping(value = "/getOrderInfoById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderInfoById(@ApiParam(value = "订单ID", required = true) Integer id) {
        if (id == null) {
            return new Result().erro("订单ID为空");
        }
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder != null) {
            // 封装结果map
            Map<String, Object> map = MapUtil.newHashMap();
            // 收货地址ID
            Integer addressId = userOrder.getAddressId();
            // 收货地址
            UserAddress userAddress = userAddressService.selectOne(new EntityWrapper<UserAddress>()
                    .eq(UserAddress.ID, addressId)
                    .eq(UserAddress.IS_DEL, 0));
            map.put("address", userAddress);
            return new Result().success(map);
        } else {
            return new Result().success();
        }
    }

    @ApiOperation(value = "商家端-订单列表", notes = "不传=全部订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "status", value = "0待付款,1待发货,3=完成,7=售后",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getShopOrderByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopOrderByUserId(Page page, Integer userId, Integer status, String goodsName) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
        Integer shopId = managerUser.getShopId();
        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();
        Page orderPage = userOrderService.getShopOrderByUserId(page, ew, shopId, status, goodsName);
        return new Result().success(orderPage);
    }

    @ApiOperation(value = "用户端&商家端-取消订单")
    @RequestMapping(value = "/cancelOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result cancelOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(id);
        userOrder.setStatus(6);
        userOrder.updateById();
        return new Result().success("取消成功!");
    }

    @ApiOperation(value = "商家端-确认发货")
    @RequestMapping(value = "/sendOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(id);
        userOrder.setStatus(2);
        userOrder.updateById();
        return new Result().success("发货成功!");
    }

    @ApiOperation(value = "用户端-删除订单")
    @RequestMapping(value = "/delOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(id);
        userOrder.setIsDel(1);
        userOrder.updateById();
        return new Result().success("删除成功!");
    }

    @ApiOperation(value = "用户端-申请售后")
    @RequestMapping(value = "/addOrderService", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addOrderService(@ModelAttribute com.jh.jsuk.entity.UserOrderService userOrderService) {
        userOrderService.insert();
        return new Result().success("操作成功!");
    }

    @ApiOperation(value = "用户端-更换商品")
    @RequestMapping(value = "/changeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result changeGoods(@ApiParam(value = "规格ID", required = true) Integer goodsSizeId) {
        List<ShopGoodsSize> goodsSizeList = shopGoodsSizeService.selectList(new EntityWrapper<ShopGoodsSize>()
                .eq(ShopGoodsSize.ID, goodsSizeId)
                .eq(ShopGoodsSize.IS_DEL, 0));
        return new Result().success(goodsSizeList);
    }

    @ApiOperation(value = "商家端-确认换货")
    @RequestMapping(value = "/enterChangeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterChangeGoods(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().success("没有该订单", null);
        } else {
            userOrder.setStatus(9);
            userOrder.updateById();
            return new Result().success("操作成功!");
        }
    }

    @ApiOperation(value = "商家端-确认退款")
    @RequestMapping(value = "/enterTuiKuan", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterTuiKuan(@ApiParam(value = "订单ID", required = true) Integer id,
                               @ApiParam(value = "退款金额", required = true) String price) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().erro("订单信息为空");
        } else {
            try {
                // 微信退款
                MSGUtil msgUtil = WxPay.wxPayRefund(Double.parseDouble(price), userOrder.getOrderNum());
                if (msgUtil.getState() == 500) {
                    return new Result().erro(msgUtil.getMsg(), msgUtil.getData());
                } else {
                    List<UserOrderGoods> userOrderGoods = userOrderGoodsService.selectList(new EntityWrapper<UserOrderGoods>()
                            .eq(UserOrderGoods.ORDER_ID, userOrder.getId()));
                    for (UserOrderGoods orderGoods : userOrderGoods) {
                        // 规格ID
                        Integer sizeId = orderGoods.getGoodsSizeId();
                        // 购买的数量
                        Integer num = orderGoods.getNum();
                        // 返还库存
                        ShopGoodsSize goodsSize = shopGoodsSizeService.selectOne(new EntityWrapper<ShopGoodsSize>()
                                .eq(ShopGoodsSize.ID, sizeId));
                        Integer stock = goodsSize.getStock();
                        stock = stock + num;
                        goodsSize.setStock(stock);
                        goodsSize.updateById();
                        return new Result().success("退款成功");
                    }
                }
            } catch (Exception e) {
                return new Result().erro("退款失败", e);
            }
        }
        return new Result().erro("退款失败");
    }

    @ApiOperation(value = "用户端-催一催")
    @RequestMapping(value = "/pushAPush", method = {RequestMethod.POST, RequestMethod.GET})
    public Result pushAPush(@ApiParam(value = "订单ID", required = true) Integer id) {
        return new Result().setMsg(userOrderService.pushAPush(id));
    }
}

