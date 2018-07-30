package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.entity.UserOrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 后台管理系统-用户管理-用户列表
 */
public class PlatformUserVo  implements Serializable {
    /**
     * 用户
     */
    private User user;
    /*
    消费金额
     */
    private BigDecimal consumeCount;
    /**
     * 订单数量
     */
    private Integer orderCount;
    /**
     * 可用积分
     */
    private Integer integralCount;

    /**
     *
     * 后台管理系统-用户详情
     */
    /*
    地址
     */
    private List<UserAddress> userAddressList;
    /*
    订单列表
     */
    private List<UserOrder> userOrderList;

    public List<UserAddress> getUserAddressList() {
        return userAddressList;
    }

    public void setUserAddressList(List<UserAddress> userAddressList) {
        this.userAddressList = userAddressList;
    }

    public List<UserOrder> getUserOrderList() {
        return userOrderList;
    }

    public void setUserOrderList(List<UserOrder> userOrderList) {
        this.userOrderList = userOrderList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(BigDecimal consumeCount) {
        this.consumeCount = consumeCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getIntegralCount() {
        return integralCount;
    }

    public void setIntegralCount(Integer integralCount) {
        this.integralCount = integralCount;
    }
}
