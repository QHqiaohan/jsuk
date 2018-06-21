package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserOrderGoods;

public class OrderGoodsNameVo extends UserOrderGoods {
    private ShopGoodsVo shopGoods;

    public ShopGoodsVo getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(ShopGoodsVo shopGoods) {
        this.shopGoods = shopGoods;
    }
}
