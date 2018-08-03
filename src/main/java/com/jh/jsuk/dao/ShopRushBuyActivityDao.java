package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.entity.vo.RushBuyVo;
import com.jh.jsuk.entity.vo.rushbuy.RushBuyActivityVO;
import com.jh.jsuk.entity.vo.rushbuy.ShopRushBuyActivityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 秒杀活动 Mapper 接口
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
public interface ShopRushBuyActivityDao extends BaseMapper<ShopRushBuyActivity> {

    RushBuyVo findVoByGoodsId(Integer goodsId);

    List<ShopRushBuyActivityVO> page(Page page,@Param("shopId") Integer shopId);

    RushBuyActivityVO selectVo(@Param("ew") Wrapper wrapper);
}
