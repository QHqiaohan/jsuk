package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.MarketComment;
import com.jh.jsuk.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/15 18:44
 */
public class MarketCommentVo extends MarketComment {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
