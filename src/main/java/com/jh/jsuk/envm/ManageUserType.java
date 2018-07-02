package com.jh.jsuk.envm;

public enum ManageUserType {

    SHOP(2, "商家"),

    PLATFORM(1, "平台");

    private final Integer key;

    private final String value;

    ManageUserType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
