package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.vo.UserOrderVo;
import com.jh.jsuk.envm.OrderStatus;

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

    int statusCount(OrderStatus orderStatus,Integer shopId);

    List<UserOrderVo> findVoByPage(Page page, Wrapper wrapper);

    Page<UserOrderVo> findVoPage(Page page, Wrapper wrapper);

    UserOrderVo findVoById(Integer id);

    void returnStock(Integer orderId);

    void remindingOrderTaking();

    Page getOrderByUserId(Page page, Wrapper wrapper, Integer userId, Integer status, String goodsName);

    Page getShopOrderByUserId(Page page, Wrapper wrapper, Integer shopId, Integer status, String goodsName);

    Page listPage(Page page, List<String> date, String kw, OrderStatus orderStatus);
}
