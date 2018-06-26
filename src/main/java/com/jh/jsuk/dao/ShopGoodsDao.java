package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopGoodsDao extends BaseMapper<ShopGoods> {

    List<GoodsSalesPriceVo> findShopGoodsByModularId(Integer modularId);

    List<GoodsSizeVo> shopGoodsListByModularId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("modularId") Integer modularId);

    List<GoodsSalesPriceVo> shopGoodsListByAttributeId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("attributeId") Integer attributeId
            , @Param("shopId") Integer shopId);

    List<GoodsSalesPriceVo> getShopGoodsBy(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("type") Integer type,
                                           @Param("shopId") Integer shopId);

    List<GoodsSalesPriceVo> getShopGoodsOrderBySalesPrice(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("type") Integer type,
                                                          @Param("shopId") Integer shopId);

    List<GoodsSalesPriceVo> getShopGoodsByCategoryId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("categoryId") Integer categoryId);

    List getShopGoodsByServiceOrPrice(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("goodsType") Integer goodsType,
                                      @Param("lowPrice") String lowPrice, @Param("highPrice") String highPrice,
                                      @Param("categoryId") Integer categoryId);

    GoodsSizeVo getShopGoodsById(Integer id);

    List<GoodsSalesPriceVo> getShopGoodsByLikeName(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("type") Integer type,
                                                   @Param("name") String name, @Param("attributeId") Integer attributeId);

    List<GoodsSalesPriceVo> getShopGoodsOnCategoryBy(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("type") Integer type,
                                                     @Param("categoryId") Integer categoryId);

    List getShopGoodsByBrandId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("brandId") Integer brandId);

    List<GoodsSalesPriceVo> getIsRecommend(Page page, Wrapper wrapper);

    List getShopList(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("address") String address, @Param("attributeId") Integer attributeId,
                     @Param("shopModularId") Integer shopModularId, @Param("categoryId") Integer categoryId, @Param("brandId") Integer brandId,
                     @Param("name") String name, @Param("goodsType") Integer goodsType, @Param("lowPrice") String lowPrice,
                     @Param("highPrice") String highPrice, @Param("type") Integer type);
}
