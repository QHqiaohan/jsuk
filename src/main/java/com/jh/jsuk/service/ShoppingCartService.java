package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShoppingCart;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.vo.ShoppingCartVo;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-24
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    List<ShoppingCartVo> selectVoList(Wrapper wrapper);
}
