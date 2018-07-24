package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopTodayMoney;
import com.jh.jsuk.entity.User;

public class ShopTodayMoneyVo extends ShopTodayMoney {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
