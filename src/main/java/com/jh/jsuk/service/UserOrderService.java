package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.dto.ShopSubmitOrderDto;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.entity.vo.*;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.envm.OrderType;
import com.jh.jsuk.exception.MessageException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.model.Charge;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserOrderService extends IService<UserOrder> {

    int statusCount(OrderStatus orderStatus, Integer shopId);

    List<UserOrderVo> findVoByPage(Page page, Wrapper wrapper);

    Page<UserOrderVo> findVoPage(Page page, Wrapper wrapper);

    UserOrderVo findVoById(Integer id);

    void returnStock(Integer orderId);

    void remindingOrderTaking();

    Page getOrderByUserId(Page page, Wrapper wrapper, Integer userId, Integer status, String goodsName);

    Page getShopOrderByUserId(Page page, Wrapper wrapper, Integer shopId, Integer status, String goodsName);

    Page listPage(Page page, List<String> date, String kw, OrderStatus orderStatus, Integer shopId);

    UserOrderDetailVo userOrderDetail(Integer orderId);

    /**
     * 用户订单数量
     *
     * @param userId
     * @return
     */
    Integer orderCount(Integer userId);

    Page userOrder(Page page, Integer id);

    /**
     * 催一催
     *
     * @param orderId 订单id
     * @return 操作结果
     */
    String pushAPush(Integer orderId);

    String createServiceCode() throws Exception;

    /**
     * 提交订单
     *
     * @param orderDto
     * @param userId
     * @return 订单id
     * @throws Exception
     */
    List<OrderResponse> submit(SubmitOrderDto orderDto, Integer userId) throws Exception;

    /**
     * 计算订单价格
     *
     * @param orderDto
     * @return
     * @throws Exception
     */
    OrderPrice orderPrice(ShopSubmitOrderDto orderDto, OrderType orderType, Integer userId, Integer isUseIntegral) throws Exception;

    /**
     * 订单余额支付
     */
    void balancePay(List<UserOrder> userOrders) throws MessageException;

    /**
     * 第三方支付
     */
    Charge thirdPay(List<UserOrder> userOrders) throws UnsupportedEncodingException, ChannelException;

    /**
     * 售后
     */
    AfterSaleVo getAddressAndPhone(Integer orderId);

    /**
     * 支付完成
     */
    PayResult payComplete(List<UserOrder> userOrders, Integer status);

    /**
     * 到店支付
     *
     * @param price   支付金额
     * @param payType 支付类型
     * @param userId
     * @param subject
     * @return charge对象
     */
    Charge payStore(String price, Integer payType, Integer userId, String subject) throws UnsupportedEncodingException, ChannelException;

    /**
     * 到店支付-支付成功
     *
     * @param shopId 商家id
     * @param price 支付金额
     */
    void payStoreComplete(Integer shopId, String price);
}
