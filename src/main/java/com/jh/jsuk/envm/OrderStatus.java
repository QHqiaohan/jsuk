package com.jh.jsuk.envm;

import com.github.tj123.common.enum2md.Envm;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
@Envm(name = "订单状态")
public enum OrderStatus {

    DUE_PAY(0, "待付款", "dpay", "待付款", "待买家付款"),

    WAIT_DELIVER(1, "待发货", "wdlr", "待卖家发货", "待发货"),

    DELIVERED(2, "已发货", "dlrd", "待收货", "待买家收货"),

    SUCCESS(3, "交易成功", "succ", "交易成功", "交易成功"),

    SERVICE(4, "售后", "rfmy", "售后", "售后"),

//    REFUND_GOODS(5, "退货", "rfgd"),

    CLOSED(6, "拒绝", "clsd", "拒绝", "拒绝"),

    CANCEL(7, "取消", "casl", "取消", "取消");

    private final Integer key;

    private final String value;

    private final String shortKey;

    private final String userText;

    private final String shopText;

    OrderStatus(Integer key, String value, String shortKey, String userText, String shopText) {
        this.key = key;
        this.value = value;
        this.shortKey = shortKey;
        this.userText = userText;
        this.shopText = shopText;
    }

}
