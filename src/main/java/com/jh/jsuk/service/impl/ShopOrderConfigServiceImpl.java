package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.tj123.common.RedisUtils;
import com.jh.jsuk.dao.ShopOrderConfigDao;
import com.jh.jsuk.entity.ShopOrderConfig;
import com.jh.jsuk.service.ShopOrderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单设置 服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
@Service
public class ShopOrderConfigServiceImpl extends ServiceImpl<ShopOrderConfigDao, ShopOrderConfig> implements ShopOrderConfigService {

    @Autowired
    RedisUtils redisUtils;

    public static final String SHOP_ORDER_CONFIG_KEY = "shop_order_config";

    @Override
    public ShopOrderConfig getConfig(Integer shopId) throws Exception {
        String key = SHOP_ORDER_CONFIG_KEY + shopId;
        ShopOrderConfig config = redisUtils.get(key, ShopOrderConfig.class);
        if (config != null)
            return config.defaultConfig();
        ShopOrderConfig dbConfig = _getConfig(shopId);
        if (dbConfig != null) {
            redisUtils.set(key, dbConfig.defaultConfig(), 10 * 60);
            return dbConfig;
        }
        return new ShopOrderConfig().defaultConfig();
    }

    private ShopOrderConfig _getConfig(Integer shopId) {
        Wrapper<ShopOrderConfig> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopOrderConfig.SHOP_ID, shopId);
        return selectOne(wrapper);
    }
}
