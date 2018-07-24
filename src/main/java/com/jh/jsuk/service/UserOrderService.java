package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.dto.ShopSubmitOrderDto;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.entity.vo.OrderPrice;
import com.jh.jsuk.entity.vo.OrderResponse;
import com.jh.jsuk.entity.vo.UserOrderDetailVo;
import com.jh.jsuk.entity.vo.UserOrderVo;
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

    Page getOrderByUserId(Page page, Wrapper wrapper, Integer userId, Integer status, String goodsName);

    Page getShopOrderByUserId(Page page, Wrapper wrapper, Integer shopId, Integer status, String goodsName);

    Page listPage(Page page, List<String> date, String kw, OrderStatus orderStatus);

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
    OrderPrice orderPrice(ShopSubmitOrderDto orderDto, OrderType orderType, Integer userId) throws Exception;

    /**
     * 订单余额支付
     */
    void balancePay(UserOrder userOrder) throws MessageException;
}
