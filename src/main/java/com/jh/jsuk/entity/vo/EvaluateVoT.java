package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserEvaluate;
import com.jh.jsuk.entity.UserOrder;

/**
 * @author xieshihao
 * @date 2018-04-10 10:51
 */
public class EvaluateVoT extends UserEvaluate {
    private User user;
    private Shop shop;
    private UserOrder order;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public UserOrder getOrder() {
        return order;
    }

    public void setOrder(UserOrder order) {
        this.order = order;
    }
}
