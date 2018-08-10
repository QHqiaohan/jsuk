package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserTiXian;

import java.io.Serializable;

/**
 * 平台-用户提现
 */
public class UserTiXianVo extends UserTiXian implements Serializable {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
