package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.entity.UserTiXian;

import java.io.Serializable;

/**
 * 平台-商家端提现
 */
public class ShopUserTiXianVo extends UserTiXian implements Serializable {

    private ShopUser shopUser;

    public ShopUser getShopUser() {
        return shopUser;
    }

    public void setShopUser(ShopUser shopUser) {
        this.shopUser = shopUser;
    }

}
