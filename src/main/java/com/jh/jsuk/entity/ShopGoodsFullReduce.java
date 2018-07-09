package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 满减活动表
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@TableName("js_shop_goods_full_reduce")
public class ShopGoodsFullReduce extends Model<ShopGoodsFullReduce> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "店铺ID", required = true, name = "shopId")
    private Integer shopId;
    @ApiModelProperty(value = "商品ID", name = "goodsId")
    private Integer goodsId;
    @ApiModelProperty(value = "规格ID", name = "sizeId")
    private Integer sizeId;
    /**
     * 满多少
     */
    @ApiModelProperty(value = "满多少", required = true, name = "full")
    private String full;
    /**
     * 减多少
     */
    @ApiModelProperty(value = "减多少", required = true, name = "reduce")
    private String reduce;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getShopId() {
        return shopId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";
    public static final String GOODS_ID = "goods_id";

    public static final String SIZE_ID = "size_id";

    public static final String FULL = "full";

    public static final String REDUCE = "reduce";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopGoodsFullReduce{" +
        "id=" + id +
                ", shopId=" + shopId +
        ", goodsId=" + goodsId +
        ", sizeId=" + sizeId +
        ", full=" + full +
        ", reduce=" + reduce +
        "}";
    }
}
