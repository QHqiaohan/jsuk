package com.jh.jsuk.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Serializable {
    private Date sendTime;
    private Integer addressId;
    private Integer shopId;
    private Integer couponId;
    private List<GoodsDTO> goodsList;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public List<GoodsDTO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsDTO> goodsList) {
        this.goodsList = goodsList;
    }
}
