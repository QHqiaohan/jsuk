package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopRushBuy;

/**
 * <p>
 * 秒杀信息配置 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopRushBuyService extends IService<ShopRushBuy> {

    Page getShopRushBuyList(Page page,Integer rushBuyId);

}
