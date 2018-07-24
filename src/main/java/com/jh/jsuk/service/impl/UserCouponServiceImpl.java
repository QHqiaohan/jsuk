package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserCouponDao;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.UserCoupon;
import com.jh.jsuk.entity.vo.UserCouponVo;
import com.jh.jsuk.service.UserCouponService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券ID关联t_coupon 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Service
public class UserCouponServiceImpl extends ServiceImpl<UserCouponDao, UserCoupon> implements UserCouponService {

    @Override
    public List<UserCouponVo> findByUserId(Integer userId) {
        Date date = new Date();
        List<UserCouponVo> list = baseMapper.findByUserId(userId, date);
        for (UserCouponVo vo : list) {
            vo.setIsTimeOut(false);
            Coupon info = vo.getCouponInfo();
            if (info != null) {
                Date endTime = info.getEndTime();
                if (endTime != null)
                    if (date.getTime() > endTime.getTime()) {
                        vo.setIsTimeOut(true);
                    }
            }
        }
        return list;
    }

    @Override
    public List<UserCouponVo> listByUserShopId(Integer shopId, Integer userId) {
        Date date = new Date();
        List<UserCouponVo> list = baseMapper.listByUserShopId(shopId,userId, date);
        for (UserCouponVo vo : list) {
            vo.setIsTimeOut(false);
            Coupon info = vo.getCouponInfo();
            if (info != null) {
                Date endTime = info.getEndTime();
                if (endTime != null)
                    if (date.getTime() > endTime.getTime()) {
                        vo.setIsTimeOut(true);
                    }
            }
        }
        return list;
    }
}
