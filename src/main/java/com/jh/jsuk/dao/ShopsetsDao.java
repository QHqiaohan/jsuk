package com.jh.jsuk.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ShopSets;


/**
 * <p>
 * 商家端-今日交易额明细 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopsetsDao extends BaseMapper<ShopSets> {

    ShopSets getshopset(Integer shopid);
    ShopSets getShopSetByShopId(Integer shopId);


}
