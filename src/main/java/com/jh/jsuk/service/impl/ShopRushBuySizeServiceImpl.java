package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopRushBuySizeDao;
import com.jh.jsuk.entity.ShopRushBuySize;
import com.jh.jsuk.service.ShopRushBuySizeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀配置和商品规格关联表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Service
public class ShopRushBuySizeServiceImpl extends ServiceImpl<ShopRushBuySizeDao, ShopRushBuySize> implements ShopRushBuySizeService {

    @Override
    public Page listPage(Page page) {
        EntityWrapper<ShopRushBuySize> wrapper = new EntityWrapper<>();
        return page.setRecords(baseMapper.listPage(page,wrapper));
    }
}
