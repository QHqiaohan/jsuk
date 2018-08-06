package com.jh.jsuk.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台-系统首页
 */
public class PlateformIndexVo implements Serializable {

    //今日订单总数
    private Integer todayOrderCount;

    //今日销售总额
    private BigDecimal todaySalesAmount;

    //昨日销售总额
    private BigDecimal yesterdaySalesAmount;

    //待付款订单
    private Integer notPaidOrderCount;

    //待发货订单
    private Integer notSendOrderCount;

    //已完成订单
    private Integer finishedOrderCount;

    //已发货订单
    private Integer alreadySendOrderCount;

    //待处理退款申请
    private Integer notDealRefund;

    //已上架商品数量
    private Integer shoppingGoodsCount;

    //全部商品数量
    private Integer allGoodsCount;

    //今日新增用户数量
    private Integer todayAddingUserCount;

    //昨日新增用户数量
    private Integer yesterdayAddingUserCount;

    //本月新增用户数量
    private Integer thisMonthAddingUserCount;

    //会员总数
    private Integer vipCount;

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
    }

    public Integer getTodayOrderCount() {
        return todayOrderCount;
    }

    public void setTodayOrderCount(Integer todayOrderCount) {
        this.todayOrderCount = todayOrderCount;
    }

    public BigDecimal getTodaySalesAmount() {
        return todaySalesAmount;
    }

    public void setTodaySalesAmount(BigDecimal todaySalesAmount) {
        this.todaySalesAmount = todaySalesAmount;
    }

    public BigDecimal getYesterdaySalesAmount() {
        return yesterdaySalesAmount;
    }

    public void setYesterdaySalesAmount(BigDecimal yesterdaySalesAmount) {
        this.yesterdaySalesAmount = yesterdaySalesAmount;
    }

    public Integer getNotPaidOrderCount() {
        return notPaidOrderCount;
    }

    public void setNotPaidOrderCount(Integer notPaidOrderCount) {
        this.notPaidOrderCount = notPaidOrderCount;
    }

    public Integer getNotSendOrderCount() {
        return notSendOrderCount;
    }

    public void setNotSendOrderCount(Integer notSendOrderCount) {
        this.notSendOrderCount = notSendOrderCount;
    }

    public Integer getFinishedOrderCount() {
        return finishedOrderCount;
    }

    public void setFinishedOrderCount(Integer finishedOrderCount) {
        this.finishedOrderCount = finishedOrderCount;
    }

    public Integer getAlreadySendOrderCount() {
        return alreadySendOrderCount;
    }

    public void setAlreadySendOrderCount(Integer alreadySendOrderCount) {
        this.alreadySendOrderCount = alreadySendOrderCount;
    }

    public Integer getNotDealRefund() {
        return notDealRefund;
    }

    public void setNotDealRefund(Integer notDealRefund) {
        this.notDealRefund = notDealRefund;
    }

    public Integer getShoppingGoodsCount() {
        return shoppingGoodsCount;
    }

    public void setShoppingGoodsCount(Integer shoppingGoodsCount) {
        this.shoppingGoodsCount = shoppingGoodsCount;
    }

    public Integer getAllGoodsCount() {
        return allGoodsCount;
    }

    public void setAllGoodsCount(Integer allGoodsCount) {
        this.allGoodsCount = allGoodsCount;
    }

    public Integer getTodayAddingUserCount() {
        return todayAddingUserCount;
    }

    public void setTodayAddingUserCount(Integer todayAddingUserCount) {
        this.todayAddingUserCount = todayAddingUserCount;
    }

    public Integer getYesterdayAddingUserCount() {
        return yesterdayAddingUserCount;
    }

    public void setYesterdayAddingUserCount(Integer yesterdayAddingUserCount) {
        this.yesterdayAddingUserCount = yesterdayAddingUserCount;
    }

    public Integer getThisMonthAddingUserCount() {
        return thisMonthAddingUserCount;
    }

    public void setThisMonthAddingUserCount(Integer thisMonthAddingUserCount) {
        this.thisMonthAddingUserCount = thisMonthAddingUserCount;
    }

}
