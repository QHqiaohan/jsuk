package com.jh.jsuk.envm;

/**
 * 订单状态
 */
public enum OrderStatus {

    DUE_PAY(0, "待付款", "dpay"),

    WAIT_DELIVER(1, "待发货", "wdlr"),

    DELIVERED(2, "已发货", "dlrd"),

    SUCCESS(3, "交易成功", "succ"),

    REFUND_MONEY(4, "退款", "rfmy"),

    REFUND_GOODS(5, "退货", "rfgd"),

    CLOSED(6, "拒绝", "clsd"),

    CANCEL(7, "取消", "casl"),

    REFUND_SUCC(8,"退款成功","rfsucc");

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
