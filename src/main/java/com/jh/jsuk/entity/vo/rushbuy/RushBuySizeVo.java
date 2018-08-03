package com.jh.jsuk.entity.vo.rushbuy;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class RushBuySizeVo implements Serializable {

    private Integer id;

    private String sizeName;

    private Integer stock;

    private BigDecimal originalPrice;

    private BigDecimal salesPrice;

    private Integer killStock;

    private BigDecimal killPrice;

}
