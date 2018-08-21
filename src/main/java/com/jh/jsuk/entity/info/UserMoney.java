package com.jh.jsuk.entity.info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class UserMoney {

    /**
     * 总共的余额
     */
    private BigDecimal total;

    /**
     * 申请中的余额
     */
    private BigDecimal applying;

    /**
     * 可用余额大于
     *
     * @return
     */
    public boolean hasRemain(BigDecimal bigDecimal) {
        if (total == null || bigDecimal == null)
            return false;
        return total.compareTo(bigDecimal) >= 0;
    }

    /**
     * 可用余额大于
     *
     * @return
     */
    public boolean haRemain(){
        return hasRemain(new BigDecimal("0"));
    }

}
