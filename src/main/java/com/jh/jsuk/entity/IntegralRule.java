package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 积分抵扣规则
 * </p>
 *
 * @author tj
 * @since 2018-07-19
 */
@Setter
@Getter
@ToString
@TableName("js_integral_rule")
public class IntegralRule extends Model<IntegralRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 多少积分
     */
    private Integer integral;
    /**
     * 抵扣多少钱
     */
    private BigDecimal deduction;
    /**
     * 消费多少钱
     */
    private Integer consumption;
    /**
     * 抵扣多少积分
     */
    private Integer gainIntegral;

    public static final String ID = "id";

    public static final String INTEGRAL = "integral";

    public static final String DEDUCTION = "deduction";

    public static final String CONSUMPTION = "consumption";

    public static final String GAIN_INTEGRAL = "gain_integral";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
