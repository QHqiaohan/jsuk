package com.jh.jsuk.entity.info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class UserRemainderInfo implements Serializable {

    /**
     * 余额
     */
    private BigDecimal remainder;

    /**
     * 可用提现金额
     */
    private BigDecimal cash;

    /**
     * 可用余额大于
     *
     * @return
     */
    public boolean hasRemain(BigDecimal bigDecimal) {
        if (remainder == null || bigDecimal == null)
            return false;
        return remainder.compareTo(bigDecimal) >= 0;
    }
}
