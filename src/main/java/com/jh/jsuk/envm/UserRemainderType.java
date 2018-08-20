package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;

@Getter
@Envm(name = "用户余额类型")
public enum UserRemainderType implements BaseEnum {

    /**
     * 充值
     */
    RECHARGE(1, "充值"),

    /**
     * 消费
     */
    CONSUME(-1, "消费"),

    /**
     * 红包奖励
     */
    GET_RED_PACKET(2, "红包奖励"),

    /**
     * 提现
     */
    CASH(3, "提现"),
    /**
     * 退款
     */
    REFUND(4, "退款");

    private final Integer key;
    private final String value;

    UserRemainderType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
