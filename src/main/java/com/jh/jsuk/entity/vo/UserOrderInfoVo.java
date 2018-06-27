package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserOrder;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/27 15:09
 */
public class UserOrderInfoVo extends UserOrder {

    private Integer num;
    private BigDecimal buyPrice;
    private String sizeName;
    private String goodsName;
    private String goodsHeadImg;
    private String shopName;
    private String salesPrice;
    private String originalPrice;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsHeadImg() {
        return goodsHeadImg;
    }

    public void setGoodsHeadImg(String goodsHeadImg) {
        this.goodsHeadImg = goodsHeadImg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

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
}
