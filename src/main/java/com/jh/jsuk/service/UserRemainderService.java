package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.entity.info.UserRemainderInfo;
import com.jh.jsuk.entity.vo.UserRechargeVo;
import com.pingplusplus.exception.ChannelException;

import java.io.UnsupportedEncodingException;
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
    UserRemainderInfo getRemainder(Integer userId);

    /**
     * 用户消费了多少
     *
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
     *
     * @param userId
     * @param amount
     * @throws Exception
     */
    void consume(Integer userId, BigDecimal amount) throws Exception;
    void consume(Integer userId, BigDecimal amount,String orderNum) throws Exception;

    /**
     * 用户充值
     *
     * @param userId
     * @param amount
     * @throws Exception
     */
    void recharge(Integer userId, BigDecimal amount) throws Exception;

    /**
     * 会员充值
     *
     * @param userId   用户ID
     * @param memberId 会员配置Id
     * @param payType  支付方式
     * @return
     */
    UserRechargeVo memberCharge(Integer userId, Integer memberId, Integer payType) throws UnsupportedEncodingException, ChannelException;

    /**
     * 会员充值完成
     *
     * @param remainderId      用户余额Id
     * @param rechargeRecordId 会员充值记录ID
     * @param status           支付状态
     */
    void chargeComplete(Integer remainderId, Integer rechargeRecordId, Integer status);

    /**
     * 添加提现申请记录
     * @param userId
     * @param price
     * @param tiXianNo
     * @param bankId
     */
    void createCashApplying(Integer userId, String price, String tiXianNo, Integer bankId);

    void confirm(String no);
    void decline(String no);
}
