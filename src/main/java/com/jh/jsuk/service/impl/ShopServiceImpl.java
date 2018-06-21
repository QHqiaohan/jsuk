package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopDao;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.service.ShopMoneyService;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.service.UserRemainderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopDao, Shop> implements ShopService {

    @Autowired
    UserRemainderService userRemainderService;

    @Autowired
    ShopMoneyService shopMoneyService;

    @Override
    public boolean isExist(Integer shopId) {
        return selectCount(new EntityWrapper<Shop>().eq(Shop.ID, shopId)) > 0;
    }

    /**
     * 涉及到写的操作避免幻读
     *
     * @param shopId
     * @param userId
     * @param bigDecimal
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void doDeal(Integer shopId, Integer userId, BigDecimal bigDecimal) throws Exception {
        userRemainderService.consume(userId, bigDecimal);
        shopMoneyService.makeIncome(shopId, bigDecimal);
    }

    @Override
    public ShopTelPhoneVo getShopById(Integer shopId) {
        return baseMapper.getShopById(shopId);
    }

    @Override
    public List<Shop> findCollectByUserId(Integer userId) {
        return baseMapper.findCollectByUserId(userId);
    }

}
