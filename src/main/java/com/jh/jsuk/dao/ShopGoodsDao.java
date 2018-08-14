package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.*;
import com.jh.jsuk.entity.vo.rushbuy.SGoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    List<GoodsSalesPriceVo> findShopGoodsByModularId( @Param("modularId")Integer modularId,  @Param("cityId")Integer cityId);

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

    List<GoodsSalesPriceVo> getIsRecommend(Page page, @Param("ew") Wrapper wrapper);

    List getShopList(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("address") String address, @Param("attributeId") Integer attributeId,
                     @Param("shopModularId") Integer shopModularId, @Param("categoryId") Integer categoryId, @Param("brandId") Integer brandId,
                     @Param("name") String name, @Param("goodsType") Integer goodsType, @Param("lowPrice") String lowPrice,
                     @Param("highPrice") String highPrice, @Param("type") Integer type, @Param("shopId") Integer shopId);

    List<GoodsSizeVo> findShopGoodsAndGoodsSizeByShopId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("shopId") Integer shopId, @Param("goodsName") String goodsName);

    List<GoodsSizeVo> shopGoodsList(Page page, @Param("status") Integer status, @Param("categoryId") String categoryId,
                                    @Param("keyWord") String keyWord, @Param("brandId") String brandId, @Param("shopId") Integer shopId);

    List<ShopGoodsVo2> shopGoodsRecycleList(Page page, @Param("categoryId") String categoryId,
                                            @Param("keyWord") String keyWord, @Param("brandId") String brandId, @Param("shopId") Integer shopId);

    List<GoodsSizeVo> getShopGoodsByKeywords(String keywords);

    @Select("select goods_name name from js_shop_goods where id = #{goodsId}")
    SGoodsVo shortVo(Integer goodsId);

    List<GoodsVo2> guessYourLike(@Param("cityId")Integer cityId);

    List<GoodsSalesPriceVo2> selectLevel2Goods(@Param("categoryId") Integer categoryId);

    GoodsInfoVo queryGoodsInfoVoBy(@Param("goodsId") Integer goodsId);
}
