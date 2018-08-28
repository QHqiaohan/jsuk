package com.jh.jsuk.envm;

import com.github.tj123.common.enum2md.Envm;
import lombok.Getter;

@Getter
@Envm(name = "商家余额类型")
public enum ShopMoneyType implements BaseEnum {

    /**
     * 收入
     */
    GAIN(1, "收入"),

    /**
     * 提现
     */
    CASH(0, "提现"),

    /**
     * 退款
     */
    REFUND(2,"退款");

    private final Integer key;
    private final String value;

    ShopMoneyType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
