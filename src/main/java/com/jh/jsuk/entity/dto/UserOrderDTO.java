package com.jh.jsuk.entity.dto;

import java.io.Serializable;

public class UserOrderDTO implements Serializable {
    private Integer userId;
    private Integer orderId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
