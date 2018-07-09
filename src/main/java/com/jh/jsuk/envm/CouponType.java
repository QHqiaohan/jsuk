package com.jh.jsuk.envm;

import lombok.Getter;

/**
 * 优惠券类型 1:全平台 0:商家
 */
@Getter
public enum CouponType {

    WHOLE_PRESENTED(1, "全场赠券");

    private final Integer key;

    private final String value;

    CouponType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}
