package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShoppingCartDao;
import com.jh.jsuk.entity.ShoppingCart;
import com.jh.jsuk.entity.vo.GoodsVo;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import com.jh.jsuk.service.IntegralRuleService;
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

    @Override
    public List<ShoppingCartVo> selectVoList(String userId, String goodsName) {
        List<ShoppingCartVo> list = baseMapper.selectVoList(userId, goodsName);
        for (ShoppingCartVo vo : list) {
            if (vo == null) {
                continue;
            }
            List<GoodsVo> goods = vo.getGoods();
            if (goods == null) {
                continue;
            }
            vo.setCanGetCoupon(integralRuleService.catGetCoupon(vo.getShopId()));
        }
        return list;
    }
}
