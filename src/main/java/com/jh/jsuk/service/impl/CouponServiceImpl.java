package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.CouponDao;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.vo.CoupQueryParam;
import com.jh.jsuk.entity.vo.CouponVo;
import com.jh.jsuk.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {



    @Override
    public void deleteCouponByShopId(Integer shopId) {
        baseMapper.deleteCouponByShopId(shopId);
    }

    @Override
    public void postCoupon(Integer shopId, double man, double jia) {
         baseMapper.postCoupon(shopId, man, jia);

    }

    @Override
    public List<Coupon> getListByShopId(Integer shopId) {
        return baseMapper.getListByShopId(shopId);
    }

    @Override
    public List<CouponVo> findByUserId(Integer userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public List<CouponVo> selectVoList(Wrapper wrapper) {
        return baseMapper.selectVoList(wrapper);
    }


    /**
     * 2018/3/16 优惠券查询问题，add
     *
     * @param param
     * @return
     */
    @Override
    public List<CouponVo> selectVoList2(CoupQueryParam param) {
        return baseMapper.selectVoList2(param);
    }

    @Override
    public List<Coupon> selectCouponList(Integer goodsId, Integer shopId) {
        return baseMapper.selectCouponList(goodsId, shopId);
    }

    @Override
    public List<Coupon> listUser(Integer shopId, Integer userId) {
        return baseMapper.listUser(shopId, userId, new Date());
    }

    @Override
    public boolean canGetCoupon(String shopId) {
        if (shopId == null)
            return false;
        EntityWrapper<Coupon> wrapper = new EntityWrapper<>();
        wrapper.eq(Coupon.SHOP_ID, shopId)
                .gt(Coupon.NUM, 0)
                .ne(Coupon.IS_DEL, 1)
                .lt(Coupon.END_TIME, new Date());
        return selectCount(wrapper) > 0;
    }

}
