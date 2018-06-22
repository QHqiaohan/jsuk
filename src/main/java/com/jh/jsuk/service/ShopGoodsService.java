package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;

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

    Page getShopGoodsBy(Page page, Wrapper wrapper, Integer type);

    Page getShopGoodsOrderBySalesPrice(Page page, Wrapper wrapper, Integer type);

    Page getShopGoodsByCategoryId(Page page, Wrapper wrapper, Integer categoryId);

    Page getShopGoodsByServiceOrPrice(Page page, Wrapper wrapper, Integer goodsType, String lowPrice, String highPrice, Integer categoryId);

    GoodsSizeVo getShopGoodsById(Integer id);

    Page getShopGoodsByLikeName(Page page, Wrapper wrapper, Integer type, String name);

    Page getShopGoodsOnCategoryBy(Page page, Wrapper wrapper, Integer type, Integer categoryId);

    Page getShopGoodsByBrandId(Page page, Wrapper wrapper, Integer brandId);
}
