package com.jh.jsuk.entity.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class ConsumeInfo {

    private BigDecimal consumeAmount;

    private int orderCount;

}
