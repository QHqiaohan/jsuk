package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopRushBuy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 秒杀信息配置 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopRushBuyDao extends BaseMapper<ShopRushBuy> {

    List getShopRushBuyList(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("startTime") Date startTime, @Param("endTime")Date endTime);
}
