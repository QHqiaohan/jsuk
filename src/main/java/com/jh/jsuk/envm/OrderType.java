package com.jh.jsuk.envm;

import lombok.Getter;

@Getter
public enum  OrderType {

    NORMAL(0,"普通订单"),

    RUSH_BUY(1,"秒杀订单");

    private final int key;

    private final String value;

    OrderType(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
