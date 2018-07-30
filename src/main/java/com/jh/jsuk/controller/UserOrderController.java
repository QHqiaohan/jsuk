package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.tj123.common.RedisUtils;
import com.jh.jsuk.conf.RedisKeys;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderGoods;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.entity.rules.AccountRule;
import com.jh.jsuk.entity.vo.UserOrderInfoVo;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.mq.RobbingOrderProducer;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.*;
import com.jh.jsuk.utils.wx.WxPay;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    RedisUtils redisUtils;

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
    private ShopGoodsService shopGoodsService;

    @Autowired
    NewsService newsService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    private ManagerUserService managerUserService;

    @Autowired
    private CouponService couponService;

    @GetMapping("/page")
    public R userOrderPage(Page page, String[] date, String kw, String status) throws Exception {
        OrderStatus orderStatus = null;
        if (status != null && !"all".equals(status)) {
            orderStatus = EnumUitl.toEnum(OrderStatus.class, status, "getShortKey");
        }
        return R.succ(userOrderService.listPage(page, date == null ? null : Arrays.asList(date), kw, orderStatus));
    }

    @ApiOperation(value = "用户端&商家端-订单详情/再次购买")
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
        OrderStatus[] statuses = OrderStatus.values();
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
    @ApiOperation(value = "用户端-提交订单")
    @PostMapping(value = "/submit")
    public Result submit(@RequestBody @Valid SubmitOrderDto orderDto, BindingResult result, Integer userId) throws Exception {
        Result res = new Result();
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        String key = RedisKeys.subKey(RedisKeys.PREVENT_RE_SUBMIT, session.userUid());
        if (redisUtils.hasKey(key)) {
            return res.erro("请勿重复提交");
        }
        redisUtils.setStr(key, "submit", 10);
        return res.success(userOrderService.submit(orderDto, userId));
    }

    @ApiOperation(value = "用户端-订单列表&订单关键字模糊搜索", notes = "不传=该用户全部订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "status", value = "0待付款,1待发货,2=待收货 3=售后,4=退款,5=退货,6=拒绝,7=取消",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getOrderByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderByUserId(Page page, Integer userId, Integer status, String goodsName) {
        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();
        Page orderPage = userOrderService.getOrderByUserId(page, ew, userId, status, goodsName);
        return new Result().success(orderPage);
    }

    /*@ApiOperation(value = "用户端&商家端-订单详情")
    @RequestMapping(value = "/getOrderInfoById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderInfoById(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().erro("没有该订单");
        }
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        // 收货地址ID
        Integer addressId = userOrder.getAddressId();
        // 收货地址
        UserAddress userAddress = userAddressService.selectOne(new EntityWrapper<UserAddress>()
                .eq(UserAddress.ID, addressId)
                .eq(UserAddress.IS_DEL, 0));
        UserOrderDetailVo userOrderDetailVo = new UserOrderDetailVo();
        return new Result().success(userOrderDetailVo);
    }*/

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
        if(managerUser == null)
            return R.err("用户不存在");
        Integer shopId = managerUser.getShopId();
        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();

        Page orderPage = userOrderService.getShopOrderByUserId(page, ew, shopId, status, goodsName);

        return new Result().success(orderPage);
    }

    @ApiOperation(value = "用户端&商家端-取消订单")
    @RequestMapping(value = "/cancelOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result cancelOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        userOrder.setStatus(OrderStatus.CANCEL.getKey());
        userOrder.updateById();
        return new Result().success("取消成功!");
    }

    @ApiOperation(value = "商家端-确认发货")
    @RequestMapping(value = "/sendOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        userOrder.setStatus(OrderStatus.DELIVERED.getKey());
        userOrder.updateById();
        return new Result().success("发货成功!");
    }

    @ApiOperation(value = "用户端-删除订单")
    @RequestMapping(value = "/delOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        userOrder.setIsUserDel(1);
        userOrder.updateById();
        return new Result().success("删除成功!");
    }

    @ApiOperation(value = "用户端-申请售后")
    @RequestMapping(value = "/addOrderService", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addOrderService(@ModelAttribute com.jh.jsuk.entity.UserOrderService userOrderService1) {
        UserOrder userOrder = userOrderService.selectById(userOrderService1.getOrderId());
        if (userOrderService1.getType()!=3){
            userOrder.setStatus(OrderStatus.REFUND_MONEY.getKey());
        }
        userOrder.updateById();
        userOrderService1.insert();
        return new Result().success("操作成功!");
    }

    @ApiOperation(value = "用户端-更换商品-选择商品型号")
    @RequestMapping(value = "/changeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result changeGoods(@ApiParam(value = "商品ID", required = true) Integer shopGoodsId) {
        List<ShopGoodsSize> goodsSizeList = shopGoodsSizeService.selectList(new EntityWrapper<ShopGoodsSize>()
                .eq(ShopGoodsSize.SHOP_GOODS_ID, shopGoodsId)
                .eq(ShopGoodsSize.IS_DEL, 0));
        return new Result().success(goodsSizeList);
    }

    @ApiOperation(value = "用户端-确认收货 ")
    @RequestMapping(value = "/confirmReceipt", method = {RequestMethod.POST, RequestMethod.GET})
    public Result confirmReceipt(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().success("没有该订单", null);
        } else {
            //交易成功
            userOrder.setStatus(OrderStatus.SUCCESS.getKey());
            userOrder.updateById();
            return new Result().success("操作成功!");
        }
    }

    @ApiOperation(value = "商家端-确认换货")
    @RequestMapping(value = "/enterChangeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterChangeGoods(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().success("没有该订单", null);
        } else {
            /*userOrder.setStatus(OrderStatus.SUCCESS.getKey());
            userOrder.updateById();*/
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



/*    @ApiOperation("商家端-订单列表")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "userId", value = "商家用户id", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "status", value = "0待付款,1待发货,3=完成,7=售后",
            paramType = "query", dataType = "integer"),

    })
    @RequestMapping(value="/getAllOrdersByShopId",method={RequestMethod.GET,RequestMethod.POST})
    public Result getAllOrdersByShopId(Integer userId, Integer status){
        //封装数据的map
        Map<String,Object> map=new HashMap<>();

        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if(managerUser==null){
            return new Result().erro("系统错误,请稍后再试");
        }
        Integer shopId = managerUser.getShopId();
        //查询店铺
        Shop shop=shopService.selectOne(new EntityWrapper<Shop>().eq(Shop.ID,shopId));
        map.put("shop",shop);

        //先根据店铺Id查询所有订单
        //status   0待付款,1待发货,3=完成,7=售后
        List<UserOrder> userOrderList=userOrderService.selectList(new EntityWrapper<UserOrder>()
                                                                       .eq(UserOrder.SHOP_ID,shopId)
                                                                       .eq(status!=null,UserOrder.STATUS,status)
                                                                       .eq(UserOrder.IS_USER_DEL,0)
                                                                       .eq(UserOrder.IS_SHOP_DEL,0)
                                                                       .eq(UserOrder.IS_CLOSED,0)
        );
        if(userOrderList==null || userOrderList.size()==0){
            return new Result().success("没有相关订单");
        }

        for(UserOrder userOrder:userOrderList){
            //遍历集合,每一个订单userOrder对应一张优惠券coupon
            Integer couponId=userOrder.getCouponId();
            Coupon coupon=couponService.selectOne(new EntityWrapper<Coupon>().eq(Coupon.ID,couponId));
           // userOrder.setCoupon(coupon);

            //遍历集合,每一个订单userOrder对应多个userOrderGoods,一个订单里面有多个商品
            Integer orderId=userOrder.getId();    //订单id
            List<UserOrderGoods> orderGoodsList=userOrderGoodsService.selectList(new EntityWrapper<UserOrderGoods>()
                                                                                 .eq(UserOrderGoods.ORDER_ID,orderId)
            );
            for(UserOrderGoods userOrderGoods:orderGoodsList){
                //遍历集合,每一个userOrderGoods对应一个shopGoodsSize
                Integer goodsId = userOrderGoods.getGoodsId();   //商品
                Integer goodsSizeId = userOrderGoods.getGoodsSizeId();
                ShopGoods shopGoods=shopGoodsService.selectOne(new EntityWrapper<ShopGoods>().eq(ShopGoods.ID,goodsId));
                ShopGoodsSize shopGoodsSize=shopGoodsSizeService.selectOne(new EntityWrapper<ShopGoodsSize>()
                                                                               .eq(ShopGoodsSize.ID,goodsSizeId));
                ShopOrderGoods shopOrderGoods = new ShopOrderGoods();
                shopOrderGoods.setShopGoods(shopGoods);
                shopOrderGoods.setShopGoodsSize(shopGoodsSize);

                userOrderGoods.setShopOrderGoods(shopOrderGoods);
            }
            //userOrder.setUserOrderGoodsList(orderGoodsList);
        }
        map.put("userOrderList",userOrderList);

        return new Result().success(map);
    }*/
}
