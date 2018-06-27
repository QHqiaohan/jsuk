package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ShoppingCart;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-24
 */
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {
    List<ShoppingCartVo> selectVoList(@Param("userId") String userId);

}
