package com.jh.jsuk.entity.dto;

import java.io.Serializable;

public class GoodsDTO implements Serializable {
    private Integer goodsId;
    private Integer num;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}