package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserRechargeRecord;

import java.io.Serializable;

public class UserRechargeRecordVo extends UserRechargeRecord implements Serializable {

    //用户名
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
