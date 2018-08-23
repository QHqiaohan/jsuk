package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserOrderGoods;
import com.jh.jsuk.dao.UserOrderGoodsDao;
import com.jh.jsuk.service.UserOrderGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单商品关联 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
@Service
public class UserOrderGoodsServiceImpl extends ServiceImpl<UserOrderGoodsDao, UserOrderGoods> implements UserOrderGoodsService {

    @Override
    public List<UserOrderGoods> getListByOrderId(Integer orderId) {
        return baseMapper.getListByOrderId(orderId);
    }
}
