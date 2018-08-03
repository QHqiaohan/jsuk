package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopRushBuyDao;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.service.ShopRushBuyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀信息配置 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopRushBuyServiceImpl extends ServiceImpl<ShopRushBuyDao, ShopRushBuy> implements ShopRushBuyService {

    @Override
    public Page getShopRushBuyList(Page page, Integer rushBuyId) {
        return page.setRecords(baseMapper.getShopRushBuyList(page, rushBuyId));
    }
}
