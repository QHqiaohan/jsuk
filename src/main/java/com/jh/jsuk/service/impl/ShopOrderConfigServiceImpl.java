package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopOrderConfigDao;
import com.jh.jsuk.entity.ShopOrderConfig;
import com.jh.jsuk.service.ShopOrderConfigService;
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


    @Override
    public ShopOrderConfig getConfig() {
        ShopOrderConfig notCachedConfig = selectById(1);
        if(notCachedConfig == null){
            notCachedConfig = new ShopOrderConfig();
        }
        return notCachedConfig.defaultConfig();
    }

    @Override
    public void setConfig(ShopOrderConfig config) {
        config.setId(1);
        if (!config.updateById()) {
            config.insert();
        }
    }
}
