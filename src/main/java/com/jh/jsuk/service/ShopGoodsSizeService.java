package com.jh.jsuk.service;

import com.jh.jsuk.entity.ShopGoodsSize;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 商品规格 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopGoodsSizeService extends IService<ShopGoodsSize> {

    /**
     * 获取库存
     * @param goodsSizeId
     * @return
     */
    Integer getStock(Integer goodsSizeId);

}
