package com.jh.jsuk.envm;

import lombok.Getter;

@Getter
public enum ShopRushBuyStatus {


    NOT_STARTED("未开始"),

    ON_GOING("进行中"),

    OVER("已经结束");

    private final String value;

    ShopRushBuyStatus(String value) {
        this.value = value;
    }
}
