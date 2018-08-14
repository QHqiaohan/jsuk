package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
public interface ShopService extends IService<Shop> {

    /**
     * 判断shop是否存在
     *
     * @param shopId
     * @return
     */
    boolean isExist(Integer shopId);

    /**
     * 商户交易
     * @param shopId
     * @param userId
     * @param bigDecimal
     * @throws Exception
     */
    void doDeal(Integer shopId, Integer userId, BigDecimal bigDecimal) throws Exception;

    ShopTelPhoneVo getShopById(Integer shopId);

    List<Shop> findCollectByUserId(Integer userId);

    /**
     * 获取当地的商家列表
     * @return
     */
    List<Shop> findShopsByUserArea(Integer cityId);
}
