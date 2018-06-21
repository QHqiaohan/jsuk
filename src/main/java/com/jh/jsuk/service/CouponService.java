package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.vo.CoupQueryParam;
import com.jh.jsuk.entity.vo.CouponVo;

import java.util.List;

/**
 * <p>
 * 优惠券 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface CouponService extends IService<Coupon> {

    List<CouponVo> findByUserId(Integer userId);

    List<CouponVo> selectVoList(Wrapper wrapper);

    List<CouponVo> selectVoList2(CoupQueryParam param);

}
