package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ActivityJoinDao;
import com.jh.jsuk.dao.ShopsetsDao;
import com.jh.jsuk.entity.ActivityJoin;
import com.jh.jsuk.entity.ShopSets;
import com.jh.jsuk.service.ActivityJoinService;
import com.jh.jsuk.service.ShopSetService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参与活动表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopSetServiceImpl extends ServiceImpl<ShopsetsDao, ShopSets> implements ShopSetService {


    @Override
    public ShopSets getShopSet(Integer shopId) {
        return baseMapper.getshopset(shopId);
    }

    @Override
    public ShopSets getShopSetByShopId(Integer shopId) {
        return baseMapper.getShopSetByShopId(shopId);
    }


}
