package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.ShoppingCart;


public class ShoppingCartVo extends ShoppingCart {
    private ShopGoods goods;
    private ManagerUser shops;
    private ShopGoodsSize sizes;

    public ShopGoodsSize getSizes() {
        return sizes;
    }

    public void setSizes(ShopGoodsSize sizes) {
        this.sizes = sizes;
    }

    public ManagerUser getShops() {
        return shops;
    }

    public void setShops(ManagerUser shops) {
        this.shops = shops;
    }

    public ShopGoods getGoods() {
        return goods;
    }

    public void setGoods(ShopGoods goods) {
        this.goods = goods;
    }
}
