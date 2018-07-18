package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@TableName("js_shopping_cart")
public class ShoppingCart extends Model<ShoppingCart> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否选中 0:否 1:是
     */
    private Integer checked;
    /**
     * 是否过期 0:否 1:是
     */
    private Integer isPast;
    /**
     * 规格ID
     */
    private Integer sizeId;

    /**
     * 是否是秒杀 1：秒杀 0：普通
     */
    private Integer isRushBuy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public void setIsRushBuy(Integer isRushBuy) {
        this.isRushBuy = isRushBuy;
    }

    public Integer getIsRushBuy() {
        return isRushBuy;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String SHOP_ID = "shop_id";

    public static final String GOODS_ID = "goods_id";

    public static final String NUM = "num";

    public static final String CREATE_TIME = "create_time";

    public static final String CHECKED = "checked";

    public static final String IS_PAST = "is_past";

    public static final String SIZE_ID = "size_id";

    public static final String IS_RUSH_BUY = "is_rush_buy";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
        "id=" + id +
        ", userId=" + userId +
        ", shopId=" + shopId +
        ", goodsId=" + goodsId +
        ", num=" + num +
        ", createTime=" + createTime +
        ", checked=" + checked +
        ", isPast=" + isPast +
        ", sizeId=" + sizeId +
        "}";
    }
}
