package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/26 14:18
 */
public class ShopGoodsKillVo extends ShopGoods {

    private String salesPrice;
    private String originalPrice;
    private String killPrice;
    private Integer killStock;

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(String killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getKillStock() {
        return killStock;
    }

    public void setKillStock(Integer killStock) {
        this.killStock = killStock;
    }
}
