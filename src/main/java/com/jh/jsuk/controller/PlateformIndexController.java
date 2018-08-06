package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.vo.PlateformIndexVo;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 平台-系统首页
 */
@RestController
@RequestMapping("/platefromIndex")
public class PlateformIndexController {

    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private UserService userService;



    //平台-首页数据统计
    @RequestMapping(value="/statisticsIndex",method = {RequestMethod.POST,RequestMethod.GET})
    public Result statisticsIndex(){
        PlateformIndexVo plateformIndexVo=new PlateformIndexVo();

        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM");
        String month=sdf2.format(date);

        //今日订单总数
        Integer todayOrderCount=userOrderService.selectCount(new EntityWrapper<UserOrder>()
            .eq(UserOrder.IS_USER_DEL,0)
            .eq(UserOrder.IS_SHOP_DEL,0)
            .eq(UserOrder.IS_CLOSED,0)
            .like(UserOrder.CREAT_TIME,today)
        );
        plateformIndexVo.setTodayOrderCount(todayOrderCount);

        //今日销售总额
        List<UserOrder> todayOrderList=userOrderService.selectList(new EntityWrapper<UserOrder>()
            .eq(UserOrder.IS_USER_DEL,0)
            .eq(UserOrder.IS_SHOP_DEL,0)
            .eq(UserOrder.IS_CLOSED,0)
            .like(UserOrder.CREAT_TIME,today)
        );
        BigDecimal todaySalesAmount=new BigDecimal("0.00");
        if(todayOrderList!=null && todayOrderList.size()>0){
            for(UserOrder userOrder:todayOrderList){
                if(userOrder.getOrderRealPrice()==null){
                    userOrder.setOrderRealPrice(new BigDecimal("0.00"));
                }
                todaySalesAmount=todaySalesAmount.add(userOrder.getOrderRealPrice());
            }
            plateformIndexVo.setTodaySalesAmount(todaySalesAmount);
        }else{
            plateformIndexVo.setTodaySalesAmount(new BigDecimal("0.00"));
        }

        //昨日销售总额
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        String yesterdayStr=sdf.format(yesterday);

        List<UserOrder> yesterdayOrderList=userOrderService.selectList(new EntityWrapper<UserOrder>()
            .eq(UserOrder.IS_USER_DEL,0)
            .eq(UserOrder.IS_SHOP_DEL,0)
            .eq(UserOrder.IS_CLOSED,0)
            .like(UserOrder.CREAT_TIME,yesterdayStr)
        );
        BigDecimal yesterdaySalesAmount=new BigDecimal("0.00");
        if(yesterdayOrderList!=null && yesterdayOrderList.size()>0){
            for(UserOrder userOrder:yesterdayOrderList){
                if(userOrder.getOrderRealPrice()==null){
                    userOrder.setOrderRealPrice(new BigDecimal("0.00"));
                }
                yesterdaySalesAmount=yesterdaySalesAmount.add(userOrder.getOrderRealPrice());
            }
            plateformIndexVo.setYesterdaySalesAmount(yesterdaySalesAmount);
        }else{
            plateformIndexVo.setYesterdaySalesAmount(new BigDecimal("0.00"));
        }

        //待付款订单
        int notPaidOrderCount=0;
        //待发货订单
        int notSendOrderCount=0;
        //已完成订单
        int finishedOrderCount=0;
        //已发货订单
        int alreadySendOrderCount=0;
        //待处理退款申请
        int notDealRefund=0;
        List<UserOrder> orderList=userOrderService.selectList(new EntityWrapper<UserOrder>()
                                                              .eq(UserOrder.IS_USER_DEL,0)
                                                              .eq(UserOrder.IS_SHOP_DEL,0)
                                                              .eq(UserOrder.IS_CLOSED,0)
        );
        if(orderList!=null && orderList.size()>0){
            for(UserOrder userOrder:orderList){
                if(userOrder.getStatus()==0){   //待付款订单
                    notPaidOrderCount++;
                }else if(userOrder.getStatus()==1){  //待发货订单
                    notSendOrderCount++;
                }else if(userOrder.getStatus()==3){  //已完成订单
                    finishedOrderCount++;
                }else if(userOrder.getStatus()==2){   //已发货订单
                    alreadySendOrderCount++;
                }else if(userOrder.getStatus()==4){  //待处理退款申请
                    notDealRefund++;
                }
            }
        }
        plateformIndexVo.setNotPaidOrderCount(notPaidOrderCount);
        plateformIndexVo.setNotSendOrderCount(notSendOrderCount);
        plateformIndexVo.setFinishedOrderCount(finishedOrderCount);
        plateformIndexVo.setAlreadySendOrderCount(alreadySendOrderCount);
        plateformIndexVo.setNotDealRefund(notDealRefund);


        //已上架商品数量
        int shoppingGoodsCount=0;
        //全部商品数量
        int allGoodsCount=0;
        List<ShopGoods> shopGoodsList=shopGoodsService.selectList(new EntityWrapper<ShopGoods>()
                                                                  .eq(ShopGoods.IS_DEL,0)
        );
        if(shopGoodsList!=null && shopGoodsList.size()>0){
            plateformIndexVo.setAllGoodsCount(shopGoodsList.size());
            for(ShopGoods shopGoods:shopGoodsList){
                if(shopGoods.getStatus()==1){    //1.在售
                    shoppingGoodsCount++;
                }
            }
            plateformIndexVo.setShoppingGoodsCount(shoppingGoodsCount);
        }


        //今日新增用户数量
        int todayAddingUserCount=0;
        //昨日新增用户数量
        int yesterdayAddingUserCount=0;
        //本月新增用户数量
        int thisMonthAddingUserCount=0;
        //会员总数
        int VIPCount=0;

        List<User> userList=userService.selectList(new EntityWrapper<>());
        if(userList!=null && userList.size()>0){
            for(User user:userList){
                if(user.getCreateTime().toString().contains(today)){
                    todayAddingUserCount++;
                }
                if(user.getCreateTime().toString().contains(yesterdayStr)){
                    yesterdayAddingUserCount++;
                }
                if(user.getCreateTime().toString().contains(month)){
                    thisMonthAddingUserCount++;
                }
                if(user.getLevel()!=0 && user.getLevel()!=null){
                    VIPCount++;
                }
            }
        }
        plateformIndexVo.setTodayAddingUserCount(todayAddingUserCount);
        plateformIndexVo.setYesterdayAddingUserCount(yesterdayAddingUserCount);
        plateformIndexVo.setThisMonthAddingUserCount(thisMonthAddingUserCount);
        plateformIndexVo.setVipCount(VIPCount);

        return new Result().success(plateformIndexVo);
    }

}
