package com.jh.jsuk.entity.vo;

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
    private Double freight;
    private String goodsSizeId;     //规格id
    private Integer stock;       //  sku库存
    private String goodsSizeName;    //规格名称
    private Integer killStock;     //秒杀库存

    public void setFreight(Double freight){
        this.freight=freight;
    }
    public Double getFreight(){
        return freight;
    }
    public String getSendJf() {
        return sendJf;
    }

    public void setSendJf(String sendJf) {
        this.sendJf = sendJf;
    }

    public String getDeductibleJf() {
        return deductibleJf;
    }

    public void setDeductibleJf(String deductibleJf) {
        this.deductibleJf = deductibleJf;
    }

    private String sendJf;      //赠送积分
    private String deductibleJf;//抵扣积分

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String brand;    //品牌

    public Integer getKillStock() {
        return killStock;
    }

    public void setKillStock(Integer killStock) {
        this.killStock = killStock;
    }



    public String getGoodsSizeName() {
        return goodsSizeName;
    }

    public void setGoodsSizeName(String goodsSizeName) {
        this.goodsSizeName = goodsSizeName;
    }

    public String getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(String goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }



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
