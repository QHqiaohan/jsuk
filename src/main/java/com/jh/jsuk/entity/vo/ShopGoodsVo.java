package com.jh.jsuk.entity.vo;

public class ShopGoodsVo {

    private String goodsName;
    private String mainImage;
    private String sizeName;
    private Integer num;
    private Integer goodsId;

    public Integer getGoodsId() {

        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {

        this.num = num;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {

        this.sizeName = sizeName;
    }

    public String getMainImage() {

        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
