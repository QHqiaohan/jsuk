package com.jh.jsuk.envm;

import lombok.Getter;

/**
 * 1 :全平台  2:公众号  3:APP
 */
@Getter
public enum CouponPlatform {

    ALL(1,"全平台"),

    OFFICIAL_ACCOUNTS(2,"公众号"),

    APP(3,"APP");

    private final Integer key;

    private final String value;

    CouponPlatform(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}
