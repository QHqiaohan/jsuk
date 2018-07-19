package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.tj123.common.RedisUtils;
import com.jh.jsuk.conf.RedisKeys;
import com.jh.jsuk.dao.ShopGoodsSizeDao;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.envm.OrderType;
import com.jh.jsuk.service.ShopGoodsSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 商品规格 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopGoodsSizeServiceImpl extends ServiceImpl<ShopGoodsSizeDao, ShopGoodsSize> implements ShopGoodsSizeService {

    @Autowired
    RedisUtils redisUtils;

    private static final long LV1_TIME = 5;

    private static final long LV2_TIME = 13;

    @Override
    public int getStock(Integer goodsSizeId, OrderType orderType) {
        Wrapper<ShopGoodsSize> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopGoodsSize.ID, goodsSizeId);
        ShopGoodsSize size = selectOne(wrapper);
        if (size == null)
            return 0;
        if (OrderType.NORMAL.equals(orderType)) {
            Integer stock = size.getStock();
            return stock == null ? 0 : stock;
        } else {
            Integer killStock = size.getKillStock();
            return killStock == null ? 0 : killStock;
        }
    }

    private static ConcurrentHashMap<Integer, Integer> sizeLock = new ConcurrentHashMap<>();

    @Override
    public int getCachedStock(Integer goodsSizeId, OrderType orderType) throws Exception {
        if (goodsSizeId == null)
            return 0;
        String keyLv1 = RedisKeys.subKey(RedisKeys.SHOP_GOODS_SIZE_STOCK, goodsSizeId + orderType.toString());
        String keyLv2 = RedisKeys.subKey(RedisKeys.SHOP_GOODS_SIZE_STOCK_LV2, goodsSizeId + orderType.toString());
        Integer lv1 = redisUtils.get(keyLv1, Integer.class);
        if (lv1 != null) {
            return lv1;
        }
        try {
            if (sizeLock.containsKey(goodsSizeId)) {
                Integer lv2 = redisUtils.get(keyLv2, Integer.class);
                return lv2 == null ? 0 : lv2;
            } else {
                /**
                 * 锁住
                 */
                sizeLock.put(goodsSizeId, 0);
                Integer stock = getStock(goodsSizeId, orderType);
                redisUtils.set(keyLv1, stock, LV1_TIME);
                redisUtils.set(keyLv2, stock, LV2_TIME);
                return stock;
            }
        } finally {
            /**
             * 解锁
             */
            sizeLock.remove(goodsSizeId);
        }
    }

    @Override
    public int getAccurateStock(Integer goodsSizeId, OrderType orderType) throws Exception {
        String key = RedisKeys.subKey(RedisKeys.SHOP_GOODS_SIZE_NONE, goodsSizeId + orderType.toString());
        if (redisUtils.hasKey(key))
            return 0;
        int stock = getStock(goodsSizeId, orderType);
        if (stock == 0)
            redisUtils.set(key, "0", 5 * 60);
        return stock;
    }

}
