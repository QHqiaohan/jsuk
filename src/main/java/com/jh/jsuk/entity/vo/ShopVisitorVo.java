package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.ShopVisit;
import com.jh.jsuk.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/19 13:28
 */
public class ShopVisitorVo extends ShopVisit {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
