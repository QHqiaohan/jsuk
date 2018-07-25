package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;

import java.io.Serializable;
import java.util.List;

public class AddGoodsVo extends ShopGoods implements Serializable {

    private List<ShopGoodsSize> shopGoodsSizeList;

    public List<ShopGoodsSize> getShopGoodsSizeList() {
        return shopGoodsSizeList;
    }

    public void setShopGoodsSizeList(List<ShopGoodsSize> shopGoodsSizeList) {
        this.shopGoodsSizeList = shopGoodsSizeList;
    }

}
