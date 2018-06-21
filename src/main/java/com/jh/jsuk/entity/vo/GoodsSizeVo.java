package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/13 12:45
 */
public class GoodsSizeVo extends ShopGoods {

    private List<ShopGoodsSize> shopGoodsSize;

    public List<ShopGoodsSize> getShopGoodsSize() {
        return shopGoodsSize;
    }

    public void setShopGoodsSize(List<ShopGoodsSize> shopGoodsSize) {
        this.shopGoodsSize = shopGoodsSize;
    }
}
