package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_goods")
public class ShopGoods extends Model<ShopGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 属性ID
     */
    private Integer attributeId;
    /**
     * 模块ID
     */
    private Integer shopModularId;
    /**
     * 是否推荐,0=不推荐,1=推荐
     */
    private Integer isRecommend;
    /**
     * 商品标签id
     */
    private Integer goodsLabelId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品详情图片
     */
    private String goodsImg;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 商品状态.0-待审核 1-在售 2-下架
     */
    private Integer status;
    /**
     * 0删除  1未删除
     */
    private Integer isDel;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 商品头图
     */
    private String mainImage;
    /**
     * 商品备注
     */
    private String goodsBreak;
    /**
     * 销售量
     */
    private Integer saleAmont;
    /**
     * 类型,1=包邮,2=促销,3=新品
     */
    private Integer goodsType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getShopModularId() {
        return shopModularId;
    }

    public void setShopModularId(Integer shopModularId) {
        this.shopModularId = shopModularId;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getGoodsLabelId() {
        return goodsLabelId;
    }

    public void setGoodsLabelId(Integer goodsLabelId) {
        this.goodsLabelId = goodsLabelId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getGoodsBreak() {
        return goodsBreak;
    }

    public void setGoodsBreak(String goodsBreak) {
        this.goodsBreak = goodsBreak;
    }

    public Integer getSaleAmont() {
        return saleAmont;
    }

    public void setSaleAmont(Integer saleAmont) {
        this.saleAmont = saleAmont;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String SHOP_MODULAR_ID = "shop_modular_id";

    public static final String IS_RECOMMEND = "is_recommend";

    public static final String GOODS_LABEL_ID = "goods_label_id";

    public static final String GOODS_NAME = "goods_name";

    public static final String GOODS_IMG = "goods_img";

    public static final String GOODS_DESC = "goods_desc";

    public static final String STATUS = "status";

    public static final String IS_DEL = "is_del";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String MAIN_IMAGE = "main_image";

    public static final String GOODS_BREAK = "goods_break";

    public static final String SALE_AMONT = "sale_amont";

    public static final String GOODS_TYPE = "goods_type";

    public static final String ATTRIBUTE_ID = "attribute_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopGoods{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", attributeId=" + attributeId +
                ", shopModularId=" + shopModularId +
                ", isRecommend=" + isRecommend +
                ", goodsLabelId=" + goodsLabelId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", status=" + status +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", mainImage='" + mainImage + '\'' +
                ", goodsBreak='" + goodsBreak + '\'' +
                ", saleAmont=" + saleAmont +
                ", goodsType=" + goodsType +
                '}';
    }
}
