package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;

import java.io.Serializable;

public class GoodsSalesPriceVo2 extends ShopGoods implements Serializable {

    private String sizeName;
    private Integer stock;
    private String salesPrice;
    private String originalPrice;

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

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
