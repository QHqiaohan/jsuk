package com.jh.jsuk.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jh.jsuk.envm.OrderResponseStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {

    private OrderResponseStatus status;

    private Integer orderId;

    private String orderNum;

    private OrderPrice orderPrice;

    private Integer payType;

    /**
     * 是不是这种状态
     *
     * @param status
     * @return
     */
    @JsonIgnore
    public boolean is(OrderResponseStatus status) {
        return status != null && status.equals(this.status);
    }


}
