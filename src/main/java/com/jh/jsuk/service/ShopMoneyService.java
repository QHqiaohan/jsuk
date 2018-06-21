package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopMoney;

import java.math.BigDecimal;

/**
 * <p>
 * 商家端-余额 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopMoneyService extends IService<ShopMoney> {

    /**
     * 获取商家的余额
     * @param shopId
     * @return
     */
    BigDecimal getShopMoney(Integer shopId);

    /**
     * 商家消费
     * @param shopId
     * @param amount
     * @throws Exception
     */
    void consume(Integer shopId,BigDecimal amount) throws Exception;

    /**
     * 商家收入
     * @param shopId
     * @param amount
     * @throws Exception
     */
    void makeIncome(Integer shopId,BigDecimal amount) throws Exception;

}
