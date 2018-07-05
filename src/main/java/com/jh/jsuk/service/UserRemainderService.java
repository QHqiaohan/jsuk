package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserRemainder;

import java.math.BigDecimal;

/**
 * <p>
 * 用户余额表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
public interface UserRemainderService extends IService<UserRemainder> {


    /**
     * 获取用户的余额
     *
     * @param userId
     * @return
     * @throws Exception
     */
    BigDecimal getRemainder(Integer userId);

    /**
     * 用户消费了多少
     * @param userId
     * @return
     */
    BigDecimal getConsumption(Integer userId);

    /**
     * 用户的余额大于 0
     *
     * @param userId
     * @return
     * @throws Exception
     */
    boolean hasRemain(Integer userId);

    /**
     * 用户的余额大于 bigDecimal
     *
     * @param userId
     * @return
     * @throws Exception
     */
    boolean hasRemain(Integer userId, BigDecimal bigDecimal);

    /**
     * 用户消费
     * @param userId
     * @param amount
     * @throws Exception
     */
    void consume(Integer userId,BigDecimal amount) throws Exception;

    /**
     * 用户充值
     * @param userId
     * @param amount
     * @throws Exception
     */
    void recharge(Integer userId, BigDecimal amount) throws Exception;


}
