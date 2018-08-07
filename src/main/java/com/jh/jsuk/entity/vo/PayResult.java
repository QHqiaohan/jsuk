package com.jh.jsuk.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author:xyl
 * Date:2018/8/7 16:19
 * Description:支付结果
 */
@Data
public class PayResult implements Serializable {
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 用户名
     */
    private String payName;
}
