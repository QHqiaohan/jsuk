package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.vo.CoupQueryParam;
import com.jh.jsuk.entity.vo.CouponVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface CouponDao extends BaseMapper<Coupon> {

    List<CouponVo> findByUserId(Integer userId);

    List<CouponVo> selectVoList(@Param("ew") Wrapper wrapper);

    List<CouponVo> selectVoList2(CoupQueryParam param);

    List<Coupon> selectCouponList(@Param("goodsId") Integer goodsId,@Param("shopId") Integer shopId);

}
