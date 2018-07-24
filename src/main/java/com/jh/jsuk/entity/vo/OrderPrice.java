package com.jh.jsuk.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class OrderPrice implements Serializable {

    /**
     * 订单原始价格
     */
    private BigDecimal orderPrice;

    /**
     * 订单实际价格
     */
    private BigDecimal orderRealPrice;

    /**
     * 优惠优惠了多少
     */
    private BigDecimal couponReduce;

    /**
     * 积分优惠了多少
     */
    private BigDecimal integralReduce;
    /**
     * 运费
     */
    private BigDecimal freight;
}
