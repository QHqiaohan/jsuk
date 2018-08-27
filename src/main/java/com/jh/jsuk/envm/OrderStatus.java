package com.jh.jsuk.envm;

import com.github.tj123.common.enum2md.Envm;

/**
 * 订单状态
 */
@Envm(name = "订单状态")
public enum OrderStatus {

    DUE_PAY(0, "待付款", "dpay"),

    WAIT_DELIVER(1, "待发货", "wdlr"),

    DELIVERED(2, "已发货", "dlrd"),

    SUCCESS(3, "交易成功", "succ"),

    SERVICE(4, "售后", "rfmy"),

//    REFUND_GOODS(5, "退货", "rfgd"),

    CLOSED(6, "拒绝", "clsd"),

    CANCEL(7, "取消", "casl");

    private final Integer key;

    private final String value;

    private final String shortKey;

    OrderStatus(Integer key, String value, String shortKey) {
        this.key = key;
        this.value = value;
        this.shortKey = shortKey;
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }

    public String getShortKey() {
        return shortKey;
    }
}
