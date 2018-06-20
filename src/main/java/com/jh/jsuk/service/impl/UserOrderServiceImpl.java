package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.dao.UserOrderDao;
import com.jh.jsuk.service.UserOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderDao, UserOrder> implements UserOrderService {

}
