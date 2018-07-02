package com.jh.jsuk.envm;

public enum ShopGoodsStatus {

    WAIT_CONFIRM(0, "待审核", "wcm"),

    UPPER(1, "上架", "upr"),

    LOWER(2, "下架", "lwr");

    private final Integer key;

    private final String value;

    private final String shortKey;

    ShopGoodsStatus(Integer key, String value, String shortKey) {
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
