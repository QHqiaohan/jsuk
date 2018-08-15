package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShoppingCartDao;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShoppingCart;
import com.jh.jsuk.entity.vo.GoodsVo;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import com.jh.jsuk.service.CouponService;
import com.jh.jsuk.service.IntegralRuleService;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Autowired
    IntegralRuleService integralRuleService;

    @Autowired
    ShopService shopService;

    @Autowired
    CouponService couponService;

    @Override
    public List<ShoppingCartVo> selectVoList(String userId, String goodsName) {
        List<ShoppingCartVo> list = baseMapper.selectVoList(userId, goodsName);
        for (ShoppingCartVo vo : list) {
            if (vo == null) {
                continue;
            }
            Shop shop = shopService.selectById(vo.getShopId());
            if(shop != null){
                vo.setShopName(shop.getShopName());
            }
            List<GoodsVo> goods = vo.getGoods();
            for(GoodsVo g:goods){
                System.out.println(g.getGoodsId()+"  "+g.getGoodsSizeId()+"  "+g.getGoodsSizeName());
            }
            if (goods == null) {
                continue;
            }
            vo.setCanGetCoupon(couponService.canGetCoupon(vo.getShopId()));
        }
        return list;
    }
}
