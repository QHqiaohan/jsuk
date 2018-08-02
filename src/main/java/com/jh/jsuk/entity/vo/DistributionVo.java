package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.DistributionUser;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author:xyl
 * Date:2018/8/1 17:49
 * Description:配送管理
 */
@Data
public class DistributionVo extends DistributionUser {
    /**
     * 收入金额
     */
    private BigDecimal income;
    /**
     * 订单数量
     */
    private Integer orderAmount;
}
