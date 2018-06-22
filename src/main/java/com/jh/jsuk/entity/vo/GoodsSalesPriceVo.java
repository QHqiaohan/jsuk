package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.ShopGoods;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/12 15:01
 */
public class GoodsSalesPriceVo extends ShopGoods {

    private String salesPrice;
    private String originalPrice;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getSalesPrice() {

        return salesPrice;
    }
}
