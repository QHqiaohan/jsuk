package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券商品关联表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_coupon_goods")
public class CouponGoods extends Model<CouponGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 优惠券ID
     */
    private Integer couponId;
    /**
     * 商品ID
     */
    private Integer goodsId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public static final String ID = "id";

    public static final String COUPON_ID = "coupon_id";

    public static final String GOODS_ID = "goods_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CouponGoods{" +
        "id=" + id +
        ", couponId=" + couponId +
        ", goodsId=" + goodsId +
        "}";
    }
}
