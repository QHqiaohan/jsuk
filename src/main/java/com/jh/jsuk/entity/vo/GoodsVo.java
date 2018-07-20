package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoodsSize;

import java.io.Serializable;

/**
 * luopa 在 2018/6/27 创建.
 */
public class GoodsVo implements Serializable {
    private String jscId;
    private String goodsId;
    private String goodsName;
    private String salesPrice;
    private String killPrice;
    private Integer num;
    private String createTime;
    private String mainImage;


    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    private Integer checked;
    private Integer isPast;

    public String getJscId() {
        return jscId;
    }

    public void setJscId(String jscId) {
        this.jscId = jscId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(String killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getIsPast() {
        return isPast;
    }

    public void setIsPast(Integer isPast) {
        this.isPast = isPast;
    }
}
