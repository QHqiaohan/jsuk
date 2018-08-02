package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.entity.vo.UserOrderServiceVo;

/**
 * <p>
 * 订单申请售后 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
public interface UserOrderServiceDao extends BaseMapper<UserOrderService> {

    UserOrderServiceVo getVo(Integer id);

}
