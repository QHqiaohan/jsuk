package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserCoupon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 优惠券ID关联t_coupon Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
public interface UserCouponDao extends BaseMapper<UserCoupon> {

    @Select({
            "select * from js_user_coupon where coupon_id = #{couponId} and user_id = #{userId}"
    })
    @ResultMap("com.jh.jsuk.dao.UserCouponDao.BaseResultMap")
    UserCoupon selectByCouponId(@Param("couponId") Integer couponId, @Param("userId") Integer userId);


}
