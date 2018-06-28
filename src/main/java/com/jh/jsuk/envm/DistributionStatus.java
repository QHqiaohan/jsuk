package com.jh.jsuk.envm;

/**
 * 配送状态 1 2 3 对应 订单状态的 已发货
 * <p>
 * 已送达对应交易成功
 */
public enum DistributionStatus {

    WAIT_ROBBING(1, "待抢单", "wrb"),

    WAIT_TAKE(2, "待取货", "wtk"),

    DELIVERING(3, "待送达", "dvg"),

    DELIVERED(4, "已送达", "dvd");

    private final Integer key;

    private final String value;

    private final String sKey;

    DistributionStatus(Integer key, String value, String sKey) {
        this.key = key;
        this.value = value;
        this.sKey = sKey;
    }

    public String getsKey() {
        return sKey;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
