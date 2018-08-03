package com.jh.jsuk.entity.vo;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartVo implements Serializable {

    private String userId;
    private String shopName;
    private String shopId;

    private Boolean canGetCoupon;

    private List<GoodsVo> goods;

    public void setCanGetCoupon(Boolean canGetCoupon) {
        this.canGetCoupon = canGetCoupon;
    }

    public Boolean getCanGetCoupon() {
        return canGetCoupon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<GoodsVo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsVo> goods) {
        this.goods = goods;
    }
}
