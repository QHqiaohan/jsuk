package com.jh.jsuk.envm;

import lombok.Getter;

/**
 * 订单退货状态
 */
@Getter
public enum OrderServiceStatus {

    PENDING(0, "待处理"),

    REFUNDING(1, "处理中"),

    COMPLETE(2, "已完成"),

    REFUSED(3, "已拒绝");

    private final Integer key;

    private final String value;

    OrderServiceStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

}
