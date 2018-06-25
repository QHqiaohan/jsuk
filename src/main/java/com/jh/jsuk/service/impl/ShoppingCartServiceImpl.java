package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShoppingCartDao;
import com.jh.jsuk.entity.ShoppingCart;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import com.jh.jsuk.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Override
    public List<ShoppingCartVo> selectVoList(Wrapper wrapper) {
        return baseMapper.selectVoList(wrapper);
    }
}
