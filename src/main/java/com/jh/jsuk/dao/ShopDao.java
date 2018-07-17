package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.ShopPhoneVo;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;

import java.util.List;

/**
 * <p>
 * 店铺 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
public interface ShopDao extends BaseMapper<Shop> {

    ShopTelPhoneVo getShopById(Integer shopId);

    List<Shop> findCollectByUserId(Integer userId);

    ShopPhoneVo selectShopPhoneById(Integer id);

    List<Shop> findShopsByUserArea(User user);
}
