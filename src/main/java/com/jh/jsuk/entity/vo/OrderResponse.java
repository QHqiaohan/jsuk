package com.jh.jsuk.entity.vo;

import com.jh.jsuk.envm.OrderResponseStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {

    private OrderResponseStatus status;

    private Integer orderId;

    private String orderNum;

    private BigDecimal price;


}
