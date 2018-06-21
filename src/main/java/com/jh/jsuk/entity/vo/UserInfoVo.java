package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.GoodsLabel;
import com.jh.jsuk.entity.User;

public class UserInfoVo extends User {
    private GoodsLabel label;

    public GoodsLabel getLabel() {
        return label;
    }

    public void setLabel(GoodsLabel label) {
        this.label = label;
    }
}
