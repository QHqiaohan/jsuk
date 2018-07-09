package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.ManagerUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponListVo extends Coupon {

    private ManagerUser userInfo;

    private Integer status;


}
