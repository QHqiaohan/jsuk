package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.comparator.DistanceComparator;
import com.jh.jsuk.entity.dto.UserOrderDTO;
import com.jh.jsuk.entity.rules.AccountRule;
import com.jh.jsuk.entity.vo.UserOrderVo;
import com.jh.jsuk.envm.NewsType;
import com.jh.jsuk.mq.RobbingOrderProducer;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    //--------------------骑手端----------------------------------------------//

    /**
     * 待抢单
     */
    @ApiOperation("骑手显示待抢单列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "longitude", value = "经度",
                    required = true, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "latitude", value = "纬度",
                    required = true, paramType = "query", dataType = "double")
    })
    @GetMapping("/grabASingle")
    public Result grabASingle(Page page, @RequestParam Double longitude, @RequestParam Double latitude) {
        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
                .isNull(UserOrder.DISTRIBUTION_USER_ID)
                .eq(UserOrder.STATUS, 2)
                .ne(UserOrder.IS_DEL, 4)
                .ne(UserOrder.IS_DEL, 5)
                .ne(UserOrder.IS_DEL, 6)
                .ne(UserOrder.IS_DEL, 7)
                .orderBy(UserOrder.CREAT_TIME, false)
        );
        if (orderVoList != null && orderVoList.size() > 0) {
            for (UserOrderVo orderVo : orderVoList) {
                orderVo.setDistance(longitude, latitude);
            }
            Collections.sort(orderVoList, new DistanceComparator());
        }
        return new Result().success(orderVoList);
    }

    @Autowired
    RobbingOrderProducer producer;

    /**
     * 配送员抢单
     */
    @ApiOperation("骑手抢单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId", value = "订单Id",
                    required = true, paramType = "query", dataType = "integer")
    })
    @PostMapping("/takingByDistribution")
    public Result takingByDistribution(@ModelAttribute UserOrderDTO userOrderDTO) {
        producer.send(userOrderDTO);
        return new Result().success();
    }


    /**
     * 待取货
     */
    @ApiOperation("骑手显示显示待取货列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer")
    })
    @GetMapping("/readyToTake")
    public Result readyToTake(Page page, Integer userId) {
        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
                .eq(UserOrder.STATUS, 3)
                .ne(UserOrder.IS_DEL, 4)
                .ne(UserOrder.IS_DEL, 5)
                .ne(UserOrder.IS_DEL, 6)
                .ne(UserOrder.IS_DEL, 7)
                .orderBy(UserOrder.CREAT_TIME, false)
        );
        return new Result().success(orderVoList);
    }

    /**
     * 配送员取货
     */
    @ApiOperation("骑手取货")
    @PostMapping("/pickUp")
    public Result pickUp(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        UserOrder order = new UserOrder();
        order.setId(orderId);
        order.setStatus(4);
        order.updateById();
        return new Result().success();
    }

    /**
     * 配送员送达
     */
    @ApiOperation("骑手送达")
    @PostMapping("/service")
    @Transactional
    public Result service(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) throws Exception {
        UserOrder order = userOrderService.selectById(orderId);
        DistributionUser user = distributionUserService.selectById(order.getDistributionUserId());
        DistributionDetail detail = new DistributionDetail();
        detail.setDetail("完成配送");
        detail.setMoney(order.getDistributionFee());

        AccountRule accountRule = new AccountRule(user, detail);
        AccountRule result = ruleEngineService.executeRuleEngine("myDetail", accountRule);
        result.updateUserAndInsertDetail();

        Shop shop = shopService.selectById(order.getManagerId());

        //商家添加money
        shop.setAccountPoint(shop.getAccountPoint().add(order.getOrderPrice()));
        //shop.addTradingVolume(order.getOrderPrice());
        shopService.updateById(shop);
        List<UserOrderGoods> orderGoodsList = userOrderGoodsService.selectList(new EntityWrapper<UserOrderGoods>()
                .eq(UserOrderGoods.ORDER_ID, orderId));

        String values = "";
        for (UserOrderGoods goods : orderGoodsList) {
            values += goods.getGoodsId() + ",";
        }

        values = values.substring(0, values.length() - 1);
        List<ShopGoods> shopGoodsList = shopGoodsService.selectList(new EntityWrapper<ShopGoods>()
                .in(ShopGoods.ID, values));
        for (ShopGoods shopGoods : shopGoodsList) {
            shopGoods.setSaleAmont(shopGoods.getSaleAmont() + 1);
            shopGoods.updateById();
        }

        order.setCompleteTime(new Date());
        order.setStatus(5);
        order.updateById();
        //赠送优惠券逻辑
        User userC = userService.selectInfoById(order.getUserId());
        UserInvitationPay itu = new UserInvitationPay();

        List<UserInvitationPay> list = userInvitationPayService.selectList(new EntityWrapper<UserInvitationPay>().eq(UserInvitationPay.USER_ID, order.getUserId()));
        if(list != null && !list.isEmpty()){
            for (UserInvitationPay userInvitationPay : list) {
                Integer invitationId = userInvitationPay.getInvitationId();
                if(invitationId == null)
                    continue;
                User userI = userService.selectInfoById(invitationId);
                List<UserInvitationPay> itus = userInvitationPayService.selectInfoByUser(userC.getId(), invitationId);
                if (itus.size() == 0) {
                    itu.setInvitationId(invitationId);
                    itu.setUserId(userC.getId());
                    userInvitationPayService.insert(itu);
                }
                int userNum = userInvitationPayService.selectInvitationNumByUser(invitationId);
                int orderNum = userOrderService.selectCount(new MyEntityWrapper<UserOrder>().eq("user_id", userC.getId()));
                if (orderNum >= 1) {
                    if (userI.getIsAvailable() != 1 && userNum == 4) {
                        //创建优惠券
                        long startTime = System.currentTimeMillis();
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, 30);
                        Date endTime = cal.getTime();
                        //发送优惠券
                        for (int i = 0; i < 10; i++) {
                            Coupon cp = new Coupon();
                            cp.setFullPrice(BigDecimal.valueOf(1));
                            cp.setDiscount(BigDecimal.valueOf(20));
                            cp.setStartTime(new Date(startTime));
                            cp.setEndTime(endTime);
                            cp.setNum(1);
                            addCoupon(cp);
                            UserCoupon up = new UserCoupon();
                            up.setUserId(invitationId);
                            up.setStatus(1);
                            up.setCouponId(cp.getId());
                            up.setPublishTime(new Date(startTime));
                            sendCouponForUser(up);
                            cp.setNum(0);
                            cp.updateById();
                        }
                        User uc = new User();
                        uc.setId(userC.getId());
                        uc.setIsAvailable(1);
                        uc.updateById();
                    }
                }

            }
        }


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
        News news = new News();
        news.setNewsType(NewsType.SYS);
        news.setTitle("");
        news.setContent("这就是您的菜！妈妈期待您的五星好评~");
        newsService.push(news, order.getUserId());
        return new Result().success();
    }

    public void addCoupon(Coupon coupon) {
        coupon.insert();
    }

    public void sendCouponForUser(UserCoupon up) {
        up.insert();
    }

    /**
     * 待送达
     */
    @ApiOperation("骑手显示待送达列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer")
    })
    @GetMapping("/pendingService")
    public Result pendingService(Page page, Integer userId) {
        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
                .eq(UserOrder.STATUS, 4)
                .ne(UserOrder.IS_DEL, 4)
                .ne(UserOrder.IS_DEL, 5)
                .ne(UserOrder.IS_DEL, 6)
                .ne(UserOrder.IS_DEL, 7)
                .orderBy(UserOrder.CREAT_TIME, false)
        );
        return new Result().success(orderVoList);
    }

    @ApiOperation("骑手显示已完成数量")
    @GetMapping("/distributionComplete/count")
    public Result distributionComplete(Integer userId) {
        Wrapper wrapper = new EntityWrapper()
                .eq(UserOrder.DISTRIBUTION_USER_ID, userId)
                .eq(UserOrder.STATUS, 5)
                .ne(UserOrder.IS_DEL, 4)
                .ne(UserOrder.IS_DEL, 5)
                .ne(UserOrder.IS_DEL, 6)
                .ne(UserOrder.IS_DEL, 7)
                .orderBy(UserOrder.CREAT_TIME, false);
        int i = userOrderService.selectCount(wrapper);
        return new Result().success(i);
    }


    //--------------------骑手端----------------------------------------------//


}

