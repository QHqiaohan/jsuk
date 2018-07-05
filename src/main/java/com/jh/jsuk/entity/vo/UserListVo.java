package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class UserListVo extends User {

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 订单数量
     */
    private Integer orderCount;

    /**
     * 消费记录
     */
    private BigDecimal consumption;

}
