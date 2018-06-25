package com.jh.jsuk.envm;

/**
 * 消息类型
 */
public enum NewsType {

    SYS(1, "系统消息"),
    /**
     * second hand market
     */
    SH_MKT(2, "二手市场");

    private final Integer key;

    private final String value;

    NewsType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 是否相等
     * @param key
     * @return
     */
    public boolean eq(Integer key) {
        return this.key.equals(key);
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }
}
