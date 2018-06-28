package com.jh.jsuk.envm;

/**
 * 订单状态
 */
public enum OrderStatus {

    DUE_PAY(0, "待付款"),

    // 已付款
    WAIT_DELIVER(1, "待发货"),

    // 对应配送状态
    // 待抢单 待取货 待送达
    DELIVERED(2, "已发货"),

    //已经送达
    SUCCESS(3, "交易成功"),

    APPLY_REFUND(4, "申请退款"),

    REFUNDED(5, "退款成功"),

    CLOSED(6, "交易关闭"),

    CANCEL(7, "取消");


    //0待付款  1待发货  2=已发货 3=交易成功 4=申请退款 5=退款成功 6=交易关闭

    private final Integer key;

    private final String value;

    OrderStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }
}
