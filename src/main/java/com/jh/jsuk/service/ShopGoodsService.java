package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopGoodsService extends IService<ShopGoods> {

    List<GoodsSalesPriceVo> findShopGoodsByModularId(Integer modularId);

    Page shopGoodsListByModularId(Page page, Wrapper wrapper, Integer modularId);

    Page shopGoodsListByAttributeId(Page page, Wrapper wrapper, Integer attributeId);

}
