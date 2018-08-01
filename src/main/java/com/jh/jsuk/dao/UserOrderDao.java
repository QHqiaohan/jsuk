package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.vo.AfterSaleVo;
import com.jh.jsuk.entity.vo.UserOrderDetailVo;
import com.jh.jsuk.entity.vo.UserOrderInfoVo;
import com.jh.jsuk.entity.vo.UserOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserOrderDao extends BaseMapper<UserOrder> {

    List<UserOrderVo> findVoByPage(RowBounds page, @Param("ew") Wrapper wrapper);

    UserOrderVo findVoById(Integer id);

    List<UserOrderInfoVo> getOrderByUserId(RowBounds page, @Param("ew") Wrapper wrapper, @Param("userId") Integer userId,
                                           @Param("status") Integer status, @Param("goodsName") String goodsName);

    List<UserOrderInfoVo> getShopOrderByUserId(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Integer shopId,
                              @Param("status") Integer status, @Param("goodsName") String goodsName);

    UserOrderDetailVo userOrderDetail(Integer orderId);

    int updateStock(@Param("goodsSizeId") Integer goodsSizeId,@Param("num") Integer num);

    int updateKillStock(@Param("goodsSizeId") Integer goodsSizeId,@Param("num") Integer num);

    AfterSaleVo getAddressAndPhone(@Param("orderId") Integer orderId);
}
