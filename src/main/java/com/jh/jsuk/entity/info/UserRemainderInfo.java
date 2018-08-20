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

    private BigDecimal remainder;


}
