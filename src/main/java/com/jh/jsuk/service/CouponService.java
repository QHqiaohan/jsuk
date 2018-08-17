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


    void  deleteCouponByShopId(Integer shopId);
    //商家修改满减列表
    void postCoupon(Integer shopId,double man,double jia);
    //获取商家满减列表
    List<Coupon> getListByShopId(Integer shopId);

    List<CouponVo> findByUserId(Integer userId);

    List<CouponVo> selectVoList(Wrapper wrapper);

    List<CouponVo> selectVoList2(CoupQueryParam param);

    List<Coupon> selectCouponList(Integer goodsId, Integer shopId);

    List<Coupon> listUser(Integer shopId, Integer userId);

    boolean canGetCoupon(String shopId);
}
