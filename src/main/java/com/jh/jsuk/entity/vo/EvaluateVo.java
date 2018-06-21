package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserEvaluate;

import java.util.List;

public class EvaluateVo extends UserEvaluate {
    private UserVo user;
    private List<OrderGoodsNameVo> goodsList;

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public List<OrderGoodsNameVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoodsNameVo> goodsList) {
        this.goodsList = goodsList;
    }
}
