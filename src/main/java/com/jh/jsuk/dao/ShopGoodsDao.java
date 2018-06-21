package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import com.jh.jsuk.entity.vo.ShopGoodsSizeVo;
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

    List<ShopGoodsSizeVo> findShopGoodsByModularId(Integer modularId);

    List<GoodsSizeVo> shopGoodsListByModularId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("modularId") Integer modularId);

}
