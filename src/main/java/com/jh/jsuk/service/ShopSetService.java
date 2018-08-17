package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ActivityJoin;
import com.jh.jsuk.entity.ShopSets;

/**
 * <p>
 * 参与活动表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopSetService extends IService<ShopSets> {
    ShopSets getShopSet(Integer shopId);
    ShopSets getShopSetByShopId(Integer shopId);
}
