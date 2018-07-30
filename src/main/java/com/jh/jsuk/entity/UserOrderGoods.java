package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import com.jh.jsuk.entity.vo.ShopOrderGoods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单商品关联
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
@TableName("js_user_order_goods")
public class UserOrderGoods extends Model<UserOrderGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 配送方式,0=快递,1=同城配送,3=到店自提
     */
    private Integer orderType;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 购买的数量
     */
    private Integer num;
    /**
     * 添加时间
     */
    private Date publishTime;
    /**
     * 商品规格ID
     */
    private Integer goodsSizeId;
    /**
     * 购买时商品价格
     */
    private BigDecimal goodsPrice;

    private ShopOrderGoods shopOrderGoods;

    public ShopOrderGoods getShopOrderGoods() {
        return shopOrderGoods;
    }

    public void setShopOrderGoods(ShopOrderGoods shopOrderGoods) {
        this.shopOrderGoods = shopOrderGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(Integer goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_TYPE = "order_type";

    public static final String GOODS_ID = "goods_id";

    public static final String NUM = "num";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    public static final String GOODS_PRICE = "goods_price";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserOrderGoods{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", orderType=" + orderType +
        ", goodsId=" + goodsId +
        ", num=" + num +
        ", publishTime=" + publishTime +
        ", goodsSizeId=" + goodsSizeId +
        ", goodsPrice=" + goodsPrice +
        "}";
    }
}
