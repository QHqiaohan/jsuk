package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 积分抵扣规则
 * </p>
 *
 * @author tj
 * @since 2018-07-19
 */
@TableName("js_integral_rule")
public class IntegralRule extends Model<IntegralRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 商品规格id
     */
    private Integer goodsSizeId;
    /**
     * 多少积分
     */
    private Integer integral;
    /**
     * 抵扣多少钱
     */
    private BigDecimal deduction;


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

    public Integer getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(Integer goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    public static final String INTEGRAL = "integral";

    public static final String DEDUCTION = "deduction";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IntegralRule{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", goodsSizeId=" + goodsSizeId +
        ", integral=" + integral +
        ", deduction=" + deduction +
        "}";
    }
}
