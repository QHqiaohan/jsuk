package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商家端-今日交易额明细
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_today_money")
public class ShopTodayMoney extends Model<ShopTodayMoney> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer shopId;
    private Integer userId;
    /**
     * 金额
     */
    private String price;
    private Date publishTime;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String USER_ID = "user_id";

    public static final String PRICE = "price";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopTodayMoney{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", userId=" + userId +
        ", price=" + price +
        ", publishTime=" + publishTime +
        "}";
    }
}
