package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商家端-余额
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_money")
public class ShopMoney extends Model<ShopMoney> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer shopId;
    /**
     * 商家余额
     */
    private String money;
    /**
     * 类型,0=消费,1=收入
     */
    private Integer type;
    /**
     * 操作时间
     */
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String MONEY = "money";

    public static final String TYPE = "type";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopMoney{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", money=" + money +
        ", type=" + type +
        ", publishTime=" + publishTime +
        "}";
    }
}
