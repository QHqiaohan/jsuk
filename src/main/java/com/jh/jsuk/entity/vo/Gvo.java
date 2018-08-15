package com.jh.jsuk.entity.vo;

import java.io.Serializable;

public class Gvo implements Serializable {

    private String goodsId;

    private String userId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
