package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopTodayMoney;

/**
 * <p>
 * 商家端-今日交易额明细 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopTodayMoneyService extends IService<ShopTodayMoney> {

    Page getTodayMoneyList(Page page, Wrapper wrapper, Integer shopId, String today);

}
