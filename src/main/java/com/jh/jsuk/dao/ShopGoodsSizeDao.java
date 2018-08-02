package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.entity.vo.ShopGoodsSizeRushBuyVo;
import com.jh.jsuk.entity.vo.ShopGoodsSizeVo;
import com.jh.jsuk.entity.vo.rushbuy.SGoodsVo;
import org.apache.ibatis.annotations.Select;

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

    @Select("select size_name sizeName,goods_name name from (\n" +
            "select size_name,shop_goods_id goods_id from js_shop_goods_size where id = #{id}\n" +
            ") sz\n" +
            "left JOIN js_shop_goods g on sz.goods_id = g.id")
    SGoodsVo shortVo(Integer id);


}
