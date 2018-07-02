package com.jh.jsuk.envm;

public enum UserType {

    SHOP(1, "商家", ManageUserType.SHOP, "shp"),

    DISTRIBUTION(2, "骑手", null, "dsb"),

    USER(3, "用户", null, "usr"),

    ADMIN(3, "管理员", ManageUserType.PLATFORM, "adm"),

    ROOT(4, "运维", ManageUserType.PLATFORM, "rot");

    private final Integer key;

    private final String value;

    private final ManageUserType manageUserType;

    private final String shopKey;

    UserType(Integer key, String value, ManageUserType manageUserType, String shopKey) {
        this.key = key;
        this.value = value;
        this.manageUserType = manageUserType;
        this.shopKey = shopKey;
    }

    public boolean hasManageUserType() {
        return manageUserType != null;
    }

    public ManageUserType getManageUserType() {
        return manageUserType;
    }

    public Integer getKey() {
        return key;
    }

    public String getShopKey() {
        return shopKey;
    }

    public String getValue() {
        return value;
    }
}
