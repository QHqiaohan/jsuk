package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopRushBuy;

import java.util.Date;

/**
 * <p>
 * 秒杀信息配置 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopRushBuyService extends IService<ShopRushBuy> {

    Page getShopRushBuyList(Page page, Wrapper wrapper, Date startTime, Date endTime);
}
