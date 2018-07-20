package com.jh.jsuk.service;

import com.jh.jsuk.entity.ShopGoodsSize;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.envm.OrderType;

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
     * 获取库存 (无缓存)
     * @param goodsSizeId
     * @return
     */
    int getStock(Integer goodsSizeId, OrderType orderType);

    /**
     *  获取库存 有缓存 缓存降级
     *  高并发下返回 0
     * @param goodsSizeId
     * @param orderType
     * @return
     */
    int getCachedStock(Integer goodsSizeId,OrderType orderType) throws Exception;

    /**
     * 获取精确的库存 部分缓存
     * @param goodsSizeId
     * @param orderType
     * @return
     * @throws Exception
     */
    int getAccurateStock(Integer goodsSizeId,OrderType orderType) throws Exception;

    /**
     * 获取商品的缓存了的秒杀时间
     * @param goodsSizeId
     * @return
     * @throws Exception
     */
    ShopRushBuy getCachedRushByTime(Integer goodsSizeId) throws Exception;
}
