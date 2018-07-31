package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderGoods;

import java.io.Serializable;
import java.util.List;

public class shopOrderVo extends UserOrder implements Serializable {

    private List<UserOrderGoods> userOrderGoodsList;

    public List<UserOrderGoods> getUserOrderGoodsList() {
        return userOrderGoodsList;
    }

    public void setUserOrderGoodsList(List<UserOrderGoods> userOrderGoodsList) {
        this.userOrderGoodsList = userOrderGoodsList;
    }
}
