package com.jh.jsuk.entity.jwt;

import java.util.Date;

public class JwtParam {

    private Integer userId;
    private Date loginTime;
    private Integer loginType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "JwtParam{" +
                "userId=" + userId +
                ", loginTime=" + loginTime +
                ", loginType=" + loginType +
                '}';
    }
}
