package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopRushBuyDao;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.service.ShopRushBuyService;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public Page getShopRushBuyList(Page page, Wrapper wrapper, Date startTime, Date endTime) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopRushBuyList(page, wrapper, startTime,endTime));
        return page;
    }
}
