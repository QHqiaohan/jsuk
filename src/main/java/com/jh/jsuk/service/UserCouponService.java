package com.jh.jsuk.service;

import com.jh.jsuk.entity.UserCoupon;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.vo.UserCouponVo;

import java.util.List;

/**
 * <p>
 * 优惠券ID关联t_coupon 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
public interface UserCouponService extends IService<UserCoupon> {

    List<UserCouponVo> findByUserId(Integer userId);
}
