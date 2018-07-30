package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.*;
import java.io.Serializable;


public class ShopOrderGoods implements Serializable {

    private ShopGoods shopGoods;

    private ShopGoodsSize shopGoodsSize;

    public ShopGoods getShopGoods() {
        return shopGoods;
    }

    public void setShopGoods(ShopGoods shopGoods) {
        this.shopGoods = shopGoods;
    }

    public ShopGoodsSize getShopGoodsSize() {
        return shopGoodsSize;
    }

    public void setShopGoodsSize(ShopGoodsSize shopGoodsSize) {
        this.shopGoodsSize = shopGoodsSize;
    }




}
