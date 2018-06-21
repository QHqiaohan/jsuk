package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 店铺分类属性关联商品表
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@TableName("js_shop_attribute_goods")
public class ShopAttributeGoods extends Model<ShopAttributeGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类属性ID
     */
    private Integer attributeId;
    /**
     * 店铺ID
     */
    private Integer shopId;


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

    public static final String ID = "id";

    public static final String ATTRIBUTE_ID = "attribute_id";

    public static final String GOODS_ID = "goods_id";

    public static final String SHOP_ID = "shop_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopAttributeGoods{" +
        "id=" + id +
        ", attributeId=" + attributeId +
        ", shopId=" + shopId +
        "}";
    }
}
