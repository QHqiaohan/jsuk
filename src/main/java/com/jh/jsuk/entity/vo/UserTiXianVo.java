package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserTiXian;

import java.io.Serializable;

public class UserTiXianVo extends UserTiXian implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
