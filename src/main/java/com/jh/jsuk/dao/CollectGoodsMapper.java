package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.CollectGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户商品收藏 Mapper 接口
 * </p>
 *
 * @author tj123
 * @since 2018-06-25
 */
public interface CollectGoodsMapper extends BaseMapper<CollectGoods> {

    List<GoodsSalesPriceVo> selectCollectList(Page page, @Param("userId") Integer userId);

}
