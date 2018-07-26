package com.jh.jsuk.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class RushBuyVo {

    private Date startTime;

    private Date endTime;

    private Integer goodsSizeId;

}
