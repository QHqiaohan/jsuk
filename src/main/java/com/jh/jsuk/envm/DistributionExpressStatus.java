package com.jh.jsuk.envm;

/**
 * 状态 1=待接单,2=待送货,3=待评价,0=待付款,4=已完成,5=已取消
 */
public enum DistributionExpressStatus {

    WAIT_ROBBING("待抢单", "wrb", 2),

    WAIT_TAKE("待取货", "wtk", 3),

    DELIVERING("待送达", "dvn", 4),

    COMPLETE("已完成", "cpt", 5, 6),

    CANCEL("已取消", "cnl", 0);

    private final Integer[] key;

    private final String value;

    private final String sKey;

    DistributionExpressStatus(String value, String sKey, Integer... key) {
        this.key = key;
        this.value = value;
        this.sKey = sKey;
    }

    public String getsKey() {
        return sKey;
    }

    public Integer[] getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
