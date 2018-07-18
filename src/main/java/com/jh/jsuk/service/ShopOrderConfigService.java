package com.jh.jsuk.service;

import com.jh.jsuk.entity.ShopOrderConfig;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 订单设置 服务类
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
public interface ShopOrderConfigService extends IService<ShopOrderConfig> {

    /**
     * 获取商家的订单配置 如果没有数据填充默认数据
     *
     * @param shopId
     * @return
     */
    ShopOrderConfig getConfig(Integer shopId) throws Exception;

}
