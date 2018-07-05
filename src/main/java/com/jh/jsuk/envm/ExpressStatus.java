package com.jh.jsuk.envm;

import lombok.Getter;

/**
 * 0:取消 1:待支付 2:待抢单(骑)-待接单(用) 3:待取货(骑)-待送货(用) 4:待送达(骑)-待送货(用) 5:待评价(用)-完成(骑) 6:完成
 */
@Getter
public enum  ExpressStatus {

    CANCEL(0,"取消","cancel"),

    DUE_PAY(1,"待支付","dpay"),

    /**
     * 待抢单(骑)-待接单(用)
     */
    PAYED(2,"已支付","payed"),

    /**
     * 待取货(骑)-待送货(用)
     */
    WAIT_DELIVER(3,"等待配送","wdeliver"),

    /**
     * 待送达(骑)-待送货(用)
     */
    DELIVERING(4,"配送中","delivering"),

    /**
     * 待评价(用)-完成(骑)
     */
    WAIT_EVALUATE(5,"待评价","wevaluate"),

    COMPLETE(6,"完成","complete");

    private final Integer key;

    private final String value;

    private final String shortKey;

    ExpressStatus(Integer key, String value, String shortKey) {
        this.key = key;
        this.value = value;
        this.shortKey = shortKey;
    }
}
