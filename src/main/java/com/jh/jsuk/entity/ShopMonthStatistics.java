package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商家端-月统计
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_month_statistics")
public class ShopMonthStatistics extends Model<ShopMonthStatistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer shopId;
    /**
     * 月份
     */
    private Date month;
    /**
     * 月访问人数
     */
    private Integer monthVisitor;
    /**
     * 月交易额
     */
    private String monthMoney;
    /**
     * 月订单
     */
    private Integer monthOrder;


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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getMonthVisitor() {
        return monthVisitor;
    }

    public void setMonthVisitor(Integer monthVisitor) {
        this.monthVisitor = monthVisitor;
    }

    public String getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(String monthMoney) {
        this.monthMoney = monthMoney;
    }

    public Integer getMonthOrder() {
        return monthOrder;
    }

    public void setMonthOrder(Integer monthOrder) {
        this.monthOrder = monthOrder;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String MONTH = "month";

    public static final String MONTH_VISITOR = "month_visitor";

    public static final String MONTH_MONEY = "month_money";

    public static final String MONTH_ORDER = "month_order";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopMonthStatistics{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", month=" + month +
        ", monthVisitor=" + monthVisitor +
        ", monthMoney=" + monthMoney +
        ", monthOrder=" + monthOrder +
        "}";
    }
}
