package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopTodayMoneyDao;
import com.jh.jsuk.entity.ShopTodayMoney;
import com.jh.jsuk.service.ShopTodayMoneyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商家端-今日交易额明细 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopTodayMoneyServiceImpl extends ServiceImpl<ShopTodayMoneyDao, ShopTodayMoney> implements ShopTodayMoneyService {

    @Override
    public Page getTodayMoneyList(Page page, Wrapper wrapper, Integer shopId, String today) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getTodayMoneyList(page, wrapper, shopId, today));
        return page;
    }
}
