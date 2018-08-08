package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserOrderServiceDao;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.entity.vo.UserOrderServiceVo;
import com.jh.jsuk.service.UserOrderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单申请售后 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
@Service
public class UserOrderServiceServiceImpl extends ServiceImpl<UserOrderServiceDao, UserOrderService> implements UserOrderServiceService {

    @Override
    public UserOrderServiceVo get(Integer id) {
        return baseMapper.getVo(id);
    }

    @Autowired
    com.jh.jsuk.service.UserOrderService orderService;

    @Override
    public void newService(UserOrderService userOrderService) throws Exception {
        userOrderService.setServiceCode(orderService.createServiceCode());
        userOrderService.insert();
    }
}
