package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 价格区间统计表
 * </p>
 *
 * @author xcr
 * @since 2018-06-22
 */
@TableName("js_statistics_price")
public class StatisticsPrice extends Model<StatisticsPrice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 最低价
     */
    private String lowPrice;
    /**
     * 最高价
     */
    private String highPrice;
    /**
     * 次数
     */
    private Integer count;
    /**
     * 百分比
     */
    private String percentage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public static final String ID = "id";

    public static final String LOW_PRICE = "low_price";

    public static final String HIGH_PRICE = "high_price";

    public static final String COUNT = "count";

    public static final String PERCENTAGE = "percentage";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "StatisticsPrice{" +
        "id=" + id +
        ", lowPrice=" + lowPrice +
        ", highPrice=" + highPrice +
        ", count=" + count +
        ", percentage=" + percentage +
        "}";
    }
}
