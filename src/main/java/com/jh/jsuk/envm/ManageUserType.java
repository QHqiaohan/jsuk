package com.jh.jsuk.envm;

public enum ManageUserType {

    SHOP(2, "商家"),

    CITY(3, "城市管理员"),

    PLATFORM(1, "平台管理员");

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

    public UserType toUserType() {
        if (ManageUserType.SHOP.equals(this)) {
            return UserType.SHOP;
        } else if (ManageUserType.PLATFORM.equals(this)) {
            return UserType.ADMIN;
        } else if (ManageUserType.CITY.equals(this)) {
            return UserType.CITY_ADMIN;
        }
        return null;
    }

}
