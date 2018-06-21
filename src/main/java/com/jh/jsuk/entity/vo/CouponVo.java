package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.UserCoupon;

public class CouponVo extends Coupon {
    private UserCoupon userCoupon;

    private Integer status;

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserCoupon getUserCoupon() {
        return userCoupon;
    }

    public void setUserCoupon(UserCoupon userCoupon) {
        this.userCoupon = userCoupon;
    }
}
