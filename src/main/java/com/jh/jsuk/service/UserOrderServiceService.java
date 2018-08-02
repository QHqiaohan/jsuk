package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.entity.vo.UserOrderServiceVo;

/**
 * <p>
 * 订单申请售后 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
public interface UserOrderServiceService extends IService<UserOrderService> {

    UserOrderServiceVo get(Integer id);

}
