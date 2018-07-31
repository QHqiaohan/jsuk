package com.jh.jsuk.envm;

import lombok.Getter;

/**
 * 订单回退类型
 * 1=仅退款,2=退货退款,3=换货
 */
@Getter
public enum RefundType {

    RETURN_MONEY(1, "仅退款"),

    RETURN_GOODS(2, "退货退款"),

    CHANGE_GOODS(3, "换货");

    private final Integer key;

    private final String value;

    RefundType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}
