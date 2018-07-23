package com.jh.jsuk.envm;

import lombok.Getter;

@Getter
public enum FullReduceType {

    SHOP(0,"店铺满减"),

    GOODS(1,"商品满减");


    private final int key;
    private final String value;

    FullReduceType(int key, String value) {
        this.key = key;
        this.value = value;
    }

}
