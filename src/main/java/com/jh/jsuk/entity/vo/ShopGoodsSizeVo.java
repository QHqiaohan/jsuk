package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.ShopGoods;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/12 15:01
 */
public class ShopGoodsSizeVo extends ShopGoods {

    private String salesPrice;

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getSalesPrice() {

        return salesPrice;
    }
}
