package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.ShopPhoneVo;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.entity.vo.rushbuy.SShopVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<Shop> findShopsByUserArea(@Param("cityId") Integer cityId);

    @Select("SELECT shop_name name FROM js_shop where id = #{id}")
    SShopVo shortVo(Integer id);
}
