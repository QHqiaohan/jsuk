package com.jh.jsuk.dao;

import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.vo.RushBuyVo;

/**
 * <p>
 * 秒杀活动 Mapper 接口
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
public interface ShopRushBuyActivityDao extends BaseMapper<ShopRushBuyActivity> {

    RushBuyVo findVoByGoodsSizeId(Integer goodsSizeId);

}
