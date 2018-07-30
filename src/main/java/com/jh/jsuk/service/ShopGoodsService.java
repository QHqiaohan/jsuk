package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import com.jh.jsuk.entity.vo.ShopOrderGoods;
import com.jh.jsuk.envm.ShopGoodsStatus;

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

    Page shopGoodsListByAttributeId(Page page, Wrapper wrapper, Integer attributeId, Integer shopId);

    Page getShopGoodsBy(Page page, Wrapper wrapper, Integer type, Integer shopId);

    Page getShopGoodsOrderBySalesPrice(Page page, Wrapper wrapper, Integer type, Integer shopId);

    Page getShopGoodsByCategoryId(Page page, Wrapper wrapper, Integer categoryId);

    Page getShopGoodsByServiceOrPrice(Page page, Wrapper wrapper, Integer goodsType, String lowPrice, String highPrice, Integer categoryId);

    GoodsSizeVo getShopGoodsById(Integer id);

    Page getShopGoodsByLikeName(Page page, Wrapper wrapper, Integer type, String name, Integer attributeId);

    Page getShopGoodsOnCategoryBy(Page page, Wrapper wrapper, Integer type, Integer categoryId);

    Page getShopGoodsByBrandId(Page page, Wrapper wrapper, Integer brandId);

    Page getIsRecommend(Page page, Wrapper wrapper);

    Page getShopList(Page page, Wrapper wrapper, Integer type, Integer attributeId, String name, Integer shopModularId, Integer categoryId, Integer
            brandId, String address, Integer goodsType, String lowPrice, String highPrice,Integer shopId);

    void returnStock(Integer goodsId, Integer num);

    Page findShopGoodsAndGoodsSizeByShopId(Page page, Wrapper wrapper, Integer shopId);

    Page list(Page page, ShopGoodsStatus status, String categoryId, String keyWord, String brandId, Integer shopId);

    Page listRecycle(Page page, String categoryId, String kw, String brandId, Integer shopId);

    List<GoodsSizeVo> getShopGoodsByKeywords(String keywords);

}
