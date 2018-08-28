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
     * 申请中的余额
     * @param shopId
     * @return
     */
    BigDecimal getApplying(Integer shopId);

    /**
     * 创建提现申请
     * @param shopId
     * @param amount
     * @param bankId
     * @throws Exception
     */
    void createCashApplying(Integer shopId, BigDecimal amount, String tiXianNo, Integer bankId) throws Exception;

    /**
     * 确认通过
     * @param platformNo
     */
    void confirm(String platformNo);
    void decline(String platformNo);

    /**
     * 商家收入
     * @param shopId
     * @param amount
     * @throws Exception
     */
    void gain(Integer shopId, BigDecimal amount) throws Exception;

    boolean refund(Integer shopId,BigDecimal amount) throws Exception;

}
