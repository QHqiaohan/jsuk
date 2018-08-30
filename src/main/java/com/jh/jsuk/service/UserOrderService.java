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

    /**
     * 用户端
     *
     * @param page
     * @param wrapper
     * @param userId
     * @param status
     * @param goodsName
     * @return
     */
    Page getOrderByUserId(Page page, Wrapper wrapper, Integer userId, Integer status, String goodsName) throws Exception;

    Page getShopOrderByUserId(Page page, Wrapper wrapper, Integer shopId, Integer status, String goodsName) throws Exception;

    Page listPage(Page page, List<String> date, String kw, OrderStatus orderStatus, Integer shopId);

    UserOrderDetailVo userOrderDetail(Integer orderId) throws Exception;

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
    String pushAPush(Integer orderId) throws Exception;

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
    void balancePay(List<UserOrder> userOrders, Integer userId) throws Exception;


    /**
     * 售后
     */
    AfterSaleVo getAddressAndPhone(Integer orderId);

    /**
     * 支付完成
     */
    PayResult payComplete(List<UserOrder> userOrders);

    /**
     * 商家端 确认退款
     *
     * @param id
     * @param price
     */
    void refund(Integer id, String price) throws MessageException;

    /**
     * 付款完成之后
     * 包括 在线付款和余额支付
     */
    void onPayed(List<UserOrder> userOrders, Integer userId);
}
