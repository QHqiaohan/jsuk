package com.jh.jsuk.envm;

/**
 * 订单状态
 */
public enum OrderStatus {

    DUE_PAY(0, "待付款", "dpay"),

    WAIT_DELIVER(1, "待发货", "wdlr"),

    DELIVERED(2, "已发货", "dlrd"),

    SUCCESS(3, "交易成功", "succ"),

    APPLY_REFUND(4, "申请退款", "arfd"),

    REFUNDED(5, "退款成功", "rfud"),

    CLOSED(6, "交易关闭", "clsd"),

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
