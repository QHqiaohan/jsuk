package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.entity.vo.ShopGoodsSizeVo;

import java.util.List;

/**
 * <p>
 * 商品规格 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopGoodsSizeDao extends BaseMapper<ShopGoodsSize> {

    String getSalesPriceByGoodsId(Integer goodsId);

    List<ShopGoodsSize> getGoodsSizeByGoodsId(Integer goodsId);

    List<ShopGoodsSize> selectSizeByGoodsId(Integer goodsId);

    ShopGoodsSizeVo findVoById(Integer shopGoodsSizeId);

    ShopRushBuy selectRushBuyByGoodsSizeId(Integer goodsSizeId);

}
