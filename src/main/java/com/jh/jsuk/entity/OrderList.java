package com.jh.jsuk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 购物车提交价格计算
 * Author:xyl
 * Date:2018/8/27 16:53
 * Description:
 */
@Setter
@Getter
@ToString
public class OrderList {
    /**
     * 用户地址id；
     */
    private Integer userAddressId;
    /**
     * 支付方式；1 在线支付；2货到付款
     */
    private Integer payType;
    /**
     * 配送方式 0：快递 1： 同城配送 2：到店自提
     */
    private  Integer distributionType;
    private Integer integral;//可用积分
    /**
     * 积分可抵扣
     */
    private BigDecimal integralReduce;
    /**
     * 是否使用积分：0不；1用
     */
    private Integer isUseintegral;
    /**
     * 会员类型
     */
    private String memberName;
    /**
     * 会员折扣
     */
    private BigDecimal memberzZhe;
    /**
     * 总支付价格价格
     */
    private BigDecimal zongPrice;
    /**
     * 店铺列表；
     */
    private ArrayList<OrderShops> shops;

    private Integer orderType;//是否秒杀 1是 0或null不是；

}
