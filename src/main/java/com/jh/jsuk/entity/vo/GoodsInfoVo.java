package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;

import java.io.Serializable;
import java.util.List;

/**
 * author:qiaohan
 * 2018-8-14
 */
public class GoodsInfoVo extends ShopGoods implements Serializable {

    private String brand;

    private String category;

    private List<ShopGoodsSize> sizeList;

    public List<ShopGoodsSize> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<ShopGoodsSize> sizeList) {
        this.sizeList = sizeList;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
