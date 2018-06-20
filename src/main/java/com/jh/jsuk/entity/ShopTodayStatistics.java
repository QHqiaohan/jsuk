package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商家端-今日统计数据
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_today_statistics")
public class ShopTodayStatistics extends Model<ShopTodayStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer shopId;
    /**
     * 当天日期
     */
    private Date today;
    /**
     * 今日访客
     */
    private Integer todayVisitor;
    /**
     * 今日交易额
     */
    private String todayMoney;
    /**
     * 今日订单
     */
    private Integer todayOrder;


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

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Integer getTodayVisitor() {
        return todayVisitor;
    }

    public void setTodayVisitor(Integer todayVisitor) {
        this.todayVisitor = todayVisitor;
    }

    public String getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(String todayMoney) {
        this.todayMoney = todayMoney;
    }

    public Integer getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(Integer todayOrder) {
        this.todayOrder = todayOrder;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String TODAY = "today";

    public static final String TODAY_VISITOR = "today_visitor";

    public static final String TODAY_MONEY = "today_money";

    public static final String TODAY_ORDER = "today_order";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopTodayStatistics{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", today=" + today +
        ", todayVisitor=" + todayVisitor +
        ", todayMoney=" + todayMoney +
        ", todayOrder=" + todayOrder +
        "}";
    }
}
