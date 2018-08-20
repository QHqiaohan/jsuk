package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;

@Getter
@Envm(name = "用户余额类型")
public enum UserRemainderType implements BaseEnum {

    RECHARGE(1, "充值"),

    CONSUME(-1, "消费"),

    GET_RED_PACKET(2, "红包奖励"),

    CASH(3, "提现"),

    REFUND(4,"退款");

    private final Integer key;
    private final String value;

    UserRemainderType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
