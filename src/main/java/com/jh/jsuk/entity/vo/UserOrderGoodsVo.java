package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.UserOrderGoods;

public class UserOrderGoodsVo extends UserOrderGoods {

    private ShopGoods shopGoods;

    public ShopGoods getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(ShopGoods shopGoods) {
        this.shopGoods = shopGoods;
    }
}
